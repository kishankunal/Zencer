package com.mrkunal.zencer.controller.contract;

import com.mrkunal.zencer.dto.request.bill.CreateBillRequest;
import com.mrkunal.zencer.dto.response.CompleteBillResponse;
import com.mrkunal.zencer.dto.response.StandardResponse;
import com.mrkunal.zencer.dto.response.BillResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Context;

@RequestMapping("/v1/bill")
@Tag(name = "Billing Resource", description = "Billing management API")
public interface BillingResource {
    //create bill
    // add a item and delete a item.
    //save bill
    //generate pdf
    //API to Get All Bills for a User

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<BillResponse>> createBill(@Valid @RequestBody CreateBillRequest billRequest, BindingResult bindingResult,
                                                              @Context HttpServletRequest request);

    @GetMapping(path = "/get/{billId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<CompleteBillResponse>> getBill(@PathVariable("billId") String billId,
                                                                   @Context HttpServletRequest request);

    @GetMapping(path = "/get/{billId}/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<byte[]> getPdfBill(@PathVariable("billId") String billId, @Context HttpServletRequest request);


}
