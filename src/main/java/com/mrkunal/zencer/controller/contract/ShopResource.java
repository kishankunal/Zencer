package com.mrkunal.zencer.controller.contract;

import com.mrkunal.zencer.dto.request.shop.RegisterShopRequest;
import com.mrkunal.zencer.dto.response.StandardResponse;
import com.mrkunal.zencer.dto.response.shop.ShopDetailsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Context;


@RequestMapping("/v1/shop")
@Tag(name = "Shop Resource", description = "Shop Management API")
public interface ShopResource {

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> registerShop(@Valid @RequestBody RegisterShopRequest registerShopRequest, BindingResult bindingResult,
                                                          @Context HttpServletRequest request);

    @GetMapping(path = "/activate/{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> activateShop(@PathVariable("shopId") String shopId);

    @GetMapping(path = "/deactivate/{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> deactivateShop(@PathVariable("shopId") String shopId);

    @GetMapping(path = "/{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<ShopDetailsResponse>> getShopDetails(@PathVariable("shopId") String shopId);

    @GetMapping(path = "getAllProducts/{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<ShopDetailsResponse>> getAllProducts(@PathVariable("shopId") String shopId);

}
