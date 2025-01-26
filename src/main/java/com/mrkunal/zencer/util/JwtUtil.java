package com.mrkunal.zencer.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.mrkunal.zencer.constant.Constants.SECRET_KEY_VALUE;
import static com.mrkunal.zencer.constant.DatabaseConstant.*;

@UtilityClass
public class JwtUtil {

    private static final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_VALUE.getBytes(StandardCharsets.UTF_8));

    public static String generateJwtToken(final String userId, final String userType) {
        return Jwts.builder()
                .setSubject(USER)
                .claim(USER_ID, userId)
                .claim(ROLE, userType)// Include user_id as claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token expires in 24 hours
                .signWith(secretKey)
                .compact();
    }

    public static Claims validateToken(final String token) throws JwtException {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public static String getUseridFromJwtToken(final String token) {
        Claims claims = JwtUtil.validateToken(token);
        return claims.get(USER_ID, String.class);
    }

}
