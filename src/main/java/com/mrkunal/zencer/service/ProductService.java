package com.mrkunal.zencer.service;

import com.google.inject.Inject;
import com.mrkunal.zencer.dto.request.product.AddProductRequest;
import com.mrkunal.zencer.model.Entity.Product;
import com.mrkunal.zencer.model.Entity.Shop;
import com.mrkunal.zencer.repository.ProductRepo;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    @Inject
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void addProduct(AddProductRequest request, Shop shop){
        productRepo.save(request, shop);
    }

    public Product getProduct(String productId) {
        return productRepo.getProductFromId(productId);
    }
}
