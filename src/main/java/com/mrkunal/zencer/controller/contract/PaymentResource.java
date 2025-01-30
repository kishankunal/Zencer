package com.mrkunal.zencer.controller.contract;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/payment")
@Tag(name = "Payment Resource", description = "Payment management API")
public interface PaymentResource {
    //make payments
     //only user can pay it. or agent of same shop can mark it paid
    // only agent of same shop can make any change
}
