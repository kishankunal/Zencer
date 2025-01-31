package com.mrkunal.zencer.service;

import com.google.inject.Inject;
import com.mrkunal.zencer.dto.request.shop.RegisterShopRequest;
import com.mrkunal.zencer.dto.response.product.ProductDetailsResponse;
import com.mrkunal.zencer.model.Entity.Shop;
import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.repository.ProductRepo;
import com.mrkunal.zencer.repository.ShopRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    private final ShopRepo shopRepo;
    private final ProductRepo productRepo;

    @Inject
    public ShopService(ShopRepo shopRepo, ProductRepo productRepo) {
        this.shopRepo = shopRepo;
        this.productRepo = productRepo;
    }


    public void createShop(RegisterShopRequest request, User user) {
        shopRepo.addShop(request, user);
    }

    public void activateShop(String shopId) {
        shopRepo.activateShop(shopId);
    }

    public void deactivateShop(String shopId) {
        shopRepo.deactivateShop(shopId);
    }

    public Shop getShopDetails(String shopId) {
        return shopRepo.getShopFromShopId(shopId);
    }

    public List<ProductDetailsResponse> getAllProducts(Shop shop) {
        return productRepo.getAllProducts(shop);
    }
}
