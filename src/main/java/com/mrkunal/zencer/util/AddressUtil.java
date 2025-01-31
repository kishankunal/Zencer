package com.mrkunal.zencer.util;

import com.mrkunal.zencer.model.Entity.Shop;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressUtil {

    public static String getShopAddress(Shop shop) {
        return shop.getCity() + " " + shop.getState() + " " + shop.getCountry() + " " +shop.getPincode();
    }
}
