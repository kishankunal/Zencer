package com.mrkunal.zencer.controller.contract;

import com.mrkunal.zencer.dto.request.product.AddProductRequest;
import com.mrkunal.zencer.dto.request.product.UpdatePriceProduct;
import com.mrkunal.zencer.dto.request.product.UpdateQuantityRequest;
import com.mrkunal.zencer.dto.response.StandardResponse;
import com.mrkunal.zencer.dto.response.product.ProductDetailsResponse;
import com.mrkunal.zencer.dto.response.shop.ShopDetailsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Context;
import java.util.List;

@RequestMapping("/v1/product")
@Tag(name = "Product Resource", description = "Product management API")
public interface ProductResource {
    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> addProduct(@Valid @RequestBody AddProductRequest addProductRequest,
                                                        BindingResult bindingResult, @Context HttpServletRequest request);

    @GetMapping(path = "/get/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<ProductDetailsResponse>> getProductDetails(@PathVariable("productId") String productId);

    @PostMapping(path = "/updatePrice/{productId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> updatePrice(@PathVariable("productId") String productId,
                                                         @Valid @RequestBody UpdatePriceProduct updatePriceProduct,
                                                         BindingResult bindingResult,
                                                         @Context HttpServletRequest request);

    @GetMapping(path = "/get/name/{productName}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<List<ProductDetailsResponse>>> getProductsByName(@PathVariable("productName") String productName);

    @PostMapping(path = "/updateQuantity", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> updateQuantity(@Valid @RequestBody UpdateQuantityRequest updateQuantityRequest,
                                                         BindingResult bindingResult,
                                                         @Context HttpServletRequest request);



}
