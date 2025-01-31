package com.mrkunal.zencer.controller.impl;

import com.google.inject.Inject;
import com.mrkunal.zencer.annotation.HandleApiException;
import com.mrkunal.zencer.annotation.HandleValidationError;
import com.mrkunal.zencer.annotation.JwtAuth;
import com.mrkunal.zencer.constant.GenericConstants;
import com.mrkunal.zencer.controller.contract.ShopResource;
import com.mrkunal.zencer.dto.request.shop.RegisterShopRequest;
import com.mrkunal.zencer.dto.response.StandardResponse;
import com.mrkunal.zencer.dto.response.product.ProductDetailsResponse;
import com.mrkunal.zencer.dto.response.shop.ShopDetailsResponse;
import com.mrkunal.zencer.model.Entity.Shop;
import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.repository.UserRepo;
import com.mrkunal.zencer.service.ShopService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mrkunal.zencer.util.JwtUtil.getTokenFromHeader;
import static com.mrkunal.zencer.util.JwtUtil.getUseridFromJwtToken;

@RestController
@HandleApiException
public class ShopResourceImpl implements ShopResource {

    private final ShopService shopService;
    private final UserRepo userRepo;

    @Inject
    public ShopResourceImpl(ShopService shopService, UserRepo userRepo) {
        this.shopService = shopService;
        this.userRepo = userRepo;
    }

    @Override
    @JwtAuth(roles = {GenericConstants.AGENT})
    @HandleValidationError
    public ResponseEntity<StandardResponse<String>> registerShop(RegisterShopRequest registerShopRequest,
                                                                 BindingResult bindingResult,
                                                                 HttpServletRequest request) {
        String token = getTokenFromHeader(request);
        User user = userRepo.getUserFromUserId(getUseridFromJwtToken(token));
        shopService.createShop(registerShopRequest, user);
        return ResponseEntity
                .ok(StandardResponse.success("200", "Shop Added", null));
    }

    @Override
    @JwtAuth(roles = {GenericConstants.ADMIN})
    public ResponseEntity<StandardResponse<String>> activateShop(String shopId) {
        if(shopId == null){
            return ResponseEntity.ok(
                    StandardResponse.error("400", "Shop id cannot be null", null));
        }
        shopService.activateShop(shopId);
        return ResponseEntity
                .ok(StandardResponse.success("200", "Shop activated", null));
    }

    @Override
    @JwtAuth(roles = {GenericConstants.ADMIN})
    public ResponseEntity<StandardResponse<String>> deactivateShop(String shopId) {
        if(shopId == null){
            return ResponseEntity.ok(
                    StandardResponse.error("400", "Shop id cannot be null", null));
        }
        shopService.deactivateShop(shopId);
        return ResponseEntity
                .ok(StandardResponse.success("200", "Shop deactivated", null));
    }

    @Override
    @JwtAuth
    public ResponseEntity<StandardResponse<ShopDetailsResponse>> getShopDetails(String shopId) {
        Shop shop = shopService.getShopDetails(shopId);
        return ResponseEntity.ok(
                StandardResponse.success("200", "success", ShopDetailsResponse.fromEntity(shop, null)));
    }

    @Override
    @JwtAuth
    public ResponseEntity<StandardResponse<ShopDetailsResponse>> getAllProducts(String shopId) {
        Shop shop = shopService.getShopDetails(shopId);
        List<ProductDetailsResponse> productDetailsResponses = shopService.getAllProducts(shop);
        return ResponseEntity.ok(
                StandardResponse.success("200", "success", ShopDetailsResponse.fromEntity(shop, productDetailsResponses)));
    }
}
