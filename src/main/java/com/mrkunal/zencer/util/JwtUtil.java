package com.mrkunal.zencer.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;
import java.util.Date;

@UtilityClass
public class JwtUtil {
    // Generate a secure key for HS256
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateJwtToken(String userId) {
        return Jwts.builder()
                .setSubject("user")
                .claim("userId", userId) // Include user_id as claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token expires in 24 hours
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
