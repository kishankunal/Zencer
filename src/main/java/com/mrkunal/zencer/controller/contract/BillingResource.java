package com.mrkunal.zencer.controller.contract;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/bill")
@Tag(name = "Billing Resource", description = "Billing management API")
public interface BillingResource {
    //create bill
    //save bill
    //generate pdf
    //
}
