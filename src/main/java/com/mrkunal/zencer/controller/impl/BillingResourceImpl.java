package com.mrkunal.zencer.controller.impl;

import com.mrkunal.zencer.annotation.HandleApiException;
import com.mrkunal.zencer.annotation.HandleValidationError;
import com.mrkunal.zencer.annotation.JwtAuth;
import com.mrkunal.zencer.constant.GenericConstants;
import com.mrkunal.zencer.controller.contract.BillingResource;
import com.mrkunal.zencer.dto.request.bill.CreateBillRequest;
import com.mrkunal.zencer.dto.response.BillItemResponse;
import com.mrkunal.zencer.dto.response.CompleteBillResponse;
import com.mrkunal.zencer.dto.response.StandardResponse;
import com.mrkunal.zencer.dto.response.BillResponse;
import com.mrkunal.zencer.model.Entity.Bill;
import com.mrkunal.zencer.model.Entity.Shop;
import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.service.BillingService;
import com.mrkunal.zencer.service.ShopService;
import com.mrkunal.zencer.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static com.mrkunal.zencer.util.AddressUtil.getShopAddress;
import static com.mrkunal.zencer.util.JwtUtil.*;

@RestController
@HandleApiException
public class BillingResourceImpl implements BillingResource {
    private final UserService userService;
    private final ShopService shopService;
    private final BillingService billingService;

    public BillingResourceImpl(UserService userService, ShopService shopService, BillingService billingService) {
        this.userService = userService;
        this.shopService = shopService;
        this.billingService = billingService;
    }

    @Override
    @JwtAuth(roles = {GenericConstants.AGENT})
    @HandleValidationError
    public ResponseEntity<StandardResponse<BillResponse>> createBill(CreateBillRequest billRequest,
                                                                     BindingResult bindingResult,
                                                                     HttpServletRequest request) {
        String token = getTokenFromHeader(request);
        User user = userService.getUserFromUserId(getUseridFromJwtToken(token));
        Shop shop = shopService.getShopDetails(billRequest.getShopId());
        if(shop.getUser().getUserId().equals(user.getUserId())){
            BillResponse billResponse = billingService.createBill(billRequest, shop);
            if(billResponse.getErrorDetails().size() == 0){
                billResponse.setErrorDetails(null);
                return ResponseEntity
                        .ok(StandardResponse.success("200", "Bill Created", billResponse));
            } else {
                List<String> errorList = billResponse.getErrorDetails();
                billResponse.setErrorDetails(null);
                return ResponseEntity.ok(
                        StandardResponse.successWithIssues("200", "Bill Created with issues", billResponse, errorList));

            }
        }
        return ResponseEntity.ok(
                StandardResponse.error("403", "Insufficient privileges to create this bill", null));
    }

    @Override
    @JwtAuth(roles = {GenericConstants.AGENT, GenericConstants.CUSTOMER})
    public ResponseEntity<StandardResponse<CompleteBillResponse>> getBill(String billId, HttpServletRequest request) {
        String token = getTokenFromHeader(request);
        String role = getRoleFromToken(token);
        User user = userService.getUserFromUserId(getUseridFromJwtToken(token));
        Bill bill = billingService.getBillById(billId);
        if(Objects.equals(role, GenericConstants.CUSTOMER)){
            if(bill.getUser().getUserId() != user.getUserId()){
                return ResponseEntity.ok(
                        StandardResponse.error("403", "Insufficient privileges to view this bill", null));
            }
        } else {
            if(!Objects.equals(bill.getShop().getUser().getUserId(), user.getUserId())) {
                return ResponseEntity.ok(
                        StandardResponse.error("403", "Insufficient privileges to view this bill", null));
            }
        }
        List<BillItemResponse> billItemResponses  = billingService.getBillItemDetails(bill.getBillItems());

        CompleteBillResponse completeBillResponse = new CompleteBillResponse.Builder().billItems(billItemResponses)
                .shopId(bill.getShop().getShopId())
                .shopName(bill.getShop().getShopName())
                .shopAddress(getShopAddress(bill.getShop()))
                .userId(bill.getUser().getUserId())
                .userName(bill.getUser().getName())
                .userMobileNumber(bill.getUser().getMobileNumber())
                .createdAt(bill.getCreatedAt().toString())
                .totalPrice(bill.getTotalPrice())
                .build();

        return ResponseEntity
                .ok(StandardResponse.success("200", "Success", completeBillResponse));
    }

    @Override
    @JwtAuth(roles = {GenericConstants.AGENT, GenericConstants.CUSTOMER})
    public ResponseEntity<byte[]> getPdfBill(String billId, HttpServletRequest request) {
        String token = getTokenFromHeader(request);
        String role = getRoleFromToken(token);
        User user = userService.getUserFromUserId(getUseridFromJwtToken(token));
        Bill bill = billingService.getBillById(billId);
        if(Objects.equals(role, GenericConstants.CUSTOMER)){
            if(bill.getUser().getUserId() != user.getUserId()){
                throw new RuntimeException("Insufficient privileges to view this bill");
            }
        } else {
            if(!Objects.equals(bill.getShop().getUser().getUserId(), user.getUserId())) {
                throw new RuntimeException("Insufficient privileges to view this bill");
            }
        }
        List<BillItemResponse> billItemResponses  = billingService.getBillItemDetails(bill.getBillItems());

        CompleteBillResponse completeBillResponse = new CompleteBillResponse.Builder().billItems(billItemResponses)
                .shopId(bill.getShop().getShopId())
                .shopName(bill.getShop().getShopName())
                .shopAddress(getShopAddress(bill.getShop()))
                .userId(bill.getUser().getUserId())
                .userName(bill.getUser().getName())
                .userMobileNumber(bill.getUser().getMobileNumber())
                .createdAt(bill.getCreatedAt().toString())
                .totalPrice(bill.getTotalPrice())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(billingService.generateInvoice(completeBillResponse));
    }
}
