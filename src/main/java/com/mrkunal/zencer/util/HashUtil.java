package com.mrkunal.zencer.util;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;

import static com.mrkunal.zencer.constant.GenericConstants.*;
import static com.mrkunal.zencer.constant.ExceptionMessageConstant.ERROR_WHILE_ENCRYPTING_PASSWORD;

@UtilityClass
public class HashUtil {
    public static String encryptPassword(String password) {
        try {
            return new java.math.BigInteger(
                    1, MessageDigest.getInstance(SHA_256)
                    .digest(password.getBytes())).toString(RADIX);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_WHILE_ENCRYPTING_PASSWORD, e);
        }
    }
}
