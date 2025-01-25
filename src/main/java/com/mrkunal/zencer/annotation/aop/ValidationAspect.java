package com.mrkunal.zencer.annotation.aop;

import com.mrkunal.zencer.dto.response.StandardResponse;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;


@Aspect
@Component
public class ValidationAspect {
    @Around("@annotation(com.mrkunal.zencer.annotation.HandleValidationError)")
    public Object handleValidationErrors(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Aspect triggered");
        Object[] args = joinPoint.getArgs();
        BindingResult bindingResult = null;

        // Check if the method arguments include a BindingResult
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                bindingResult = (BindingResult) arg;
                break;
            }
        }
        // Handle validation errors if present
        if (bindingResult != null && bindingResult.hasErrors()) {
            return StandardResponse.handleValidationErrors(bindingResult);
        }
        // Proceed with the method execution if there are no errors
        return joinPoint.proceed();
    }
}
