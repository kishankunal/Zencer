package com.mrkunal.zencer.controller.contract;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/offer")
@Tag(name = "Offer Resource", description = "Offer management API")
public interface OfferResource {
    //offer code applicable to all
    // for respective shop
    // offer quantity
    // max upto what price
    // add offer code, enter quantity price
    //activate and deActivate it
    // who is creating the budget will activate/deactivate
    // once budget is exhausted, offer to auto de activate
    //upate budget

}
