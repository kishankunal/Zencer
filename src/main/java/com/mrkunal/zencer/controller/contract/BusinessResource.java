package com.mrkunal.zencer.controller.contract;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/business")
@Tag(name = "Business Resource", description = "Business Api Management")
public interface BusinessResource {
    //agent or admin should add or update shop
    //admin can update any shop
    //agent can update only his shop
}
