package com.mrkunal.zencer.controller.contract;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/product")
@Tag(name = "Product Resource", description = "Product management API")
public interface ProductResource {
    //only respective agent can make changes
    //create product with description
    // update quantity - should not be -ve
    //update price
    //delete product
    // add product from csv sheet bulk add


}
