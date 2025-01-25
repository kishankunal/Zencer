package com.mrkunal.zencer.util;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;

@UtilityClass
public class HashUtil {
    public static String encryptPassword(String password) {
        try {
            return new java.math.BigInteger(
                    1, MessageDigest.getInstance("SHA-256")
                    .digest(password.getBytes())).toString(16);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting password", e);
        }
    }
}
