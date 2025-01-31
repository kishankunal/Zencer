package com.mrkunal.zencer.controller.contract;

import com.mrkunal.zencer.dto.request.product.AddProductRequest;
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

@RequestMapping("/v1/product")
@Tag(name = "Product Resource", description = "Product management API")
public interface ProductResource {
    //create product with description
    // update quantity - should not be -ve
    //update price
    //delete product
    // add product from csv sheet bulk add

    //fetch product by catagory

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> addProduct(@Valid @RequestBody AddProductRequest addProductRequest,
                                                        BindingResult bindingResult, @Context HttpServletRequest request);


    @GetMapping(path = "/get/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<ProductDetailsResponse>> getShopDetails(@PathVariable("productId") String productId);
}
