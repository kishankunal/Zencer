package com.mrkunal.zencer.util;

import com.google.inject.Inject;
import com.mrkunal.zencer.model.Entity.UserSession;
import com.mrkunal.zencer.repository.SessionRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.Index;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@UtilityClass
public class JwtUtil {
    // Generate a secure key for HS256
    private static final String SECRET_KEY_VALUE = "294e00f14e76d5e911637d578d3e6c0ab05dde4969ccf6e19a239b0448ca01f2";
    private static final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_VALUE.getBytes(StandardCharsets.UTF_8));
    private static final String USER_ID = "userId";

    public static String generateJwtToken(final String userId, final String userType) {
        return Jwts.builder()
                .setSubject("user")
                .claim("userId", userId)
                .claim("role", userType)// Include user_id as claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token expires in 24 hours
                .signWith(secretKey)
                .compact();
    }

    public static Claims validateToken(String token) throws JwtException {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getTokenId(String token) {
        Claims claims = validateToken(token);
        return claims.getId(); // Retrieve jti
    }

    public static String getUseridFromJwtToken(String token) {
        Claims claims = JwtUtil.validateToken(token);
        return claims.get(USER_ID, String.class);
    }


}
