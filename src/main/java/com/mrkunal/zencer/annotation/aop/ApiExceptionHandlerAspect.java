package com.mrkunal.zencer.annotation.aop;

import com.mrkunal.zencer.dto.response.StandardResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiExceptionHandlerAspect {

    @Around("@within(com.mrkunal.zencer.annotation.HandleApiException) || @annotation(com.mrkunal.zencer.annotation.HandleApiException)")
    public Object handleApiExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            // Log the exception (optional)
            // You can use custom response classes or standard response handling here
            return ResponseEntity.ok(StandardResponse.errorResponse(ex));
        }
    }
}
