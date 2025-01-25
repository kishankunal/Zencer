package com.mrkunal.zencer.Exception;

import com.mrkunal.zencer.dto.response.StandardResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public StandardResponse<String> handleException(Exception ex) {
        String code = ex.getClass().getSimpleName();

        return switch (code) {
            case "ValidationException" -> StandardResponse.handleException("400", "Validation Error" , ex);
            case "AuthorizationException" -> StandardResponse.handleException("403", "Authorization Error" , ex);
            case "ResourceNotFoundException" -> StandardResponse.handleException("404", "Not Found" , ex);
            default -> StandardResponse.handleException("500", "Internal Server Error" , ex);
        };
    }
}
