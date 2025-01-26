package com.mrkunal.zencer.annotation.aop;

import com.google.inject.Inject;
import com.mrkunal.zencer.annotation.JwtAuth;
import com.mrkunal.zencer.repository.SessionRepo;
import com.mrkunal.zencer.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class JwtAuthorizationAspect {

    @Autowired
    private HttpServletRequest request;

    private final SessionRepo sessionRepo;

    @Inject
    public JwtAuthorizationAspect(SessionRepo sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    @Pointcut("@annotation(com.mrkunal.zencer.annotation.JwtAuth)")
    public void authenticateJwtToken() {
        // No logic needed here, just a marker for the pointcut
    }

    // Around advice to perform authentication logic
    @Around("authenticateJwtToken()")  // Apply to methods with @JwtAuth annotation
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = getJwtFromRequest(request);

        // If the token is not present or invalid, throw exception
        if (token == null || !isValidToken(token)) {
            throw new JwtException("Invalid or missing JWT token");
        }

        // Extract the claims and role from token
        Claims claims = JwtUtil.validateToken(token);
        if(sessionRepo.isTokenBlackListed(token)){
            throw new JwtException("User authentication failed");
        }
        String role = claims.get("role", String.class);  // Extract role from token
        JwtAuth jwtAuth = getJwtAuthAnnotation(joinPoint);
        String[] requiredRoles = jwtAuth.roles();  // Get required roles from annotation

        // Check if user has the required role
        if (requiredRoles.length > 0 && !hasRequiredRole(role, requiredRoles)) {
            throw new JwtException("Insufficient privileges to access this resource");
        }

        // If valid, set the authentication in the security context
        String userId = JwtUtil.getUseridFromJwtToken(token);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority(role)))
        );

        // Proceed with the method execution
        return joinPoint.proceed();
    }

    // Extract JWT token from Authorization header
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Remove "Bearer " part
        }
        return null;
    }

    // Check if the JWT token is valid
    private boolean isValidToken(String token) {
        try {
            JwtUtil.validateToken(token);  // Validate the token
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Check if the user has the required role from the token
    private boolean hasRequiredRole(String role, String[] requiredRoles) {
        for (String requiredRole : requiredRoles) {
            if (requiredRole.equals(role)) {
                return true;
            }
        }
        return false;
    }

    // Helper method to get the JwtAuth annotation from the join point method
    private JwtAuth getJwtAuthAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        return joinPoint.getTarget().getClass()
                .getMethod(joinPoint.getSignature().getName(),
                        ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes())
                .getAnnotation(JwtAuth.class);
    }
}