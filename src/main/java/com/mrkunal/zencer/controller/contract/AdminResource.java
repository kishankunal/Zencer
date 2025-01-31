package com.mrkunal.zencer.controller.contract;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/admin")
@Tag(name = "Admin Resource", description = "Admin Management API")
public interface AdminResource {
    //make any user agent
    //update users role
}
