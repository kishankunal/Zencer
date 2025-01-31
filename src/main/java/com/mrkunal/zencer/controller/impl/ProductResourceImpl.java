package com.mrkunal.zencer.controller.impl;

import com.google.inject.Inject;
import com.mrkunal.zencer.annotation.HandleApiException;
import com.mrkunal.zencer.annotation.HandleValidationError;
import com.mrkunal.zencer.annotation.JwtAuth;
import com.mrkunal.zencer.constant.GenericConstants;
import com.mrkunal.zencer.controller.contract.ProductResource;
import com.mrkunal.zencer.dto.request.product.AddProductRequest;
import com.mrkunal.zencer.dto.request.product.UpdatePriceProduct;
import com.mrkunal.zencer.dto.request.product.UpdateQuantityRequest;
import com.mrkunal.zencer.dto.response.StandardResponse;
import com.mrkunal.zencer.dto.response.product.ProductDetailsResponse;
import com.mrkunal.zencer.model.Entity.Product;
import com.mrkunal.zencer.model.Entity.Shop;
import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.service.ProductService;
import com.mrkunal.zencer.service.ShopService;
import com.mrkunal.zencer.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mrkunal.zencer.util.JwtUtil.getTokenFromHeader;
import static com.mrkunal.zencer.util.JwtUtil.getUseridFromJwtToken;

@RestController
@HandleApiException
public class ProductResourceImpl implements ProductResource {
    private final UserService userService;
    private final ShopService shopService;
    private final ProductService productService;


    @Inject
    public ProductResourceImpl(UserService userService, ShopService shopService, ProductService productService) {
        this.userService = userService;
        this.shopService = shopService;
        this.productService = productService;
    }

    @Override
    @JwtAuth(roles = {GenericConstants.AGENT})
    @HandleValidationError
    public ResponseEntity<StandardResponse<String>> addProduct(AddProductRequest addProductRequest,
                                                               BindingResult bindingResult, HttpServletRequest request) {
        String token = getTokenFromHeader(request);
        User user = userService.getUserFromUserId(getUseridFromJwtToken(token));
        Shop shop = shopService.getShopDetails(addProductRequest.getShopId());
        if(shop.getUser().getUserId().equals(user.getUserId())){
            productService.addProduct(addProductRequest, shop);
            return ResponseEntity
                    .ok(StandardResponse.success("200", "Product Added", null));
        }
        return ResponseEntity.ok(
                StandardResponse.error("403", "Insufficient privileges to add product", null));

    }

    @Override
    @JwtAuth
    public ResponseEntity<StandardResponse<ProductDetailsResponse>> getProductDetails(String productId) {
        Product product = productService.getProduct(productId);
        return ResponseEntity
                .ok(StandardResponse.success("200", "Success",
                        ProductDetailsResponse.fromEntity(product)));
    }

    @Override
    @JwtAuth(roles = {GenericConstants.AGENT})
    @HandleValidationError
    public ResponseEntity<StandardResponse<String>> updatePrice(String productId, UpdatePriceProduct updatePriceProduct,
                                                                BindingResult bindingResult, HttpServletRequest request) {
        String token = getTokenFromHeader(request);
        Product product = productService.getProduct(productId);
        Shop shop = shopService.getShopDetails(product.getShop().getShopId());
        User user = userService.getUserFromUserId(getUseridFromJwtToken(token));
        if(shop.getUser().getUserId().equals(user.getUserId())){
            productService.updateProductsPrice(product, updatePriceProduct.getPrice());
            return ResponseEntity
                    .ok(StandardResponse.success("200", "Product's price updated", null));
        }
        return ResponseEntity.ok(
                StandardResponse.error("403", "Insufficient privileges to update the product's price", null));
    }

    @Override
    @JwtAuth
    public ResponseEntity<StandardResponse<List<ProductDetailsResponse>>> getProductsByName(String productName) {
        List<ProductDetailsResponse> productDetailsResponseList = productService.getProductsByName(productName);

        return ResponseEntity
                .ok(StandardResponse.success("200", "Success", productDetailsResponseList));
    }

    @Override
    @JwtAuth(roles = {GenericConstants.AGENT})
    @HandleValidationError
    public ResponseEntity<StandardResponse<String>> updateQuantity(UpdateQuantityRequest updateQuantityRequest, BindingResult bindingResult, HttpServletRequest request) {
        String token = getTokenFromHeader(request);
        Product product = productService.getProduct(updateQuantityRequest.getProductId().toString());
        Shop shop = shopService.getShopDetails(product.getShop().getShopId());
        User user = userService.getUserFromUserId(getUseridFromJwtToken(token));
        if(shop.getUser().getUserId().equals(user.getUserId())){
            productService.updateProductsQuantity(product, updateQuantityRequest.getQuantity(), updateQuantityRequest.getShouldIncrease());
            return ResponseEntity
                    .ok(StandardResponse.success("200", "Product's Quantity updated", null));
        }
        return ResponseEntity.ok(
                StandardResponse.error("403", "Insufficient privileges to update the product's quantity", null));
    }
}
