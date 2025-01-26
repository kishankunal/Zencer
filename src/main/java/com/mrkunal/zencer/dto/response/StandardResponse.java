package com.mrkunal.zencer.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardResponse<T>
{
        private final boolean success;
        private final String statusCode;
        private final String message;
        private  T data;
        private  List<String> errorDetails ;

    public StandardResponse(boolean success, String code, String message, List<String> errorDetails) {
        this.success = success;
        this.statusCode = code;
        this.message = message;
        this.errorDetails = errorDetails;
    }

    public StandardResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.statusCode = code;
        this.message = message;
        this.data = data;
    }


    public static <T> StandardResponse<T> error(String code, String message, List<String> errorDetails) {
        return new StandardResponse<>(false, code, message, errorDetails);
    }

    // Static factory method for success responses
    public static <T> StandardResponse<T> success(String code, String message, T data) {
        return new StandardResponse<>(true, code, message, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public List<String> getErrorDetails() {
        return errorDetails;
    }
    public static <T> StandardResponse<T> errorResponse(Exception ex) {
        String code = ex.getClass().getSimpleName();

        // List of error details (you can add more error information here as needed)
        List<String> errorDetails = List.of(ex.getMessage());

        // Create and return StandardResponse based on exception type
        return switch (code) {
            case "ValidationException" -> StandardResponse.error("400", "Validation Error", errorDetails);
            case "AuthorizationException" -> StandardResponse.error("403", "Authorization Error", errorDetails);
            case "ResourceNotFoundException" -> StandardResponse.error("404", "Resource Not Found", errorDetails);
            case "JwtException" -> StandardResponse.error("401", "JWT Authorization error", errorDetails);
            default -> StandardResponse.error("500", "Internal Server Error", errorDetails);
        };
    }

    public static <T> ResponseEntity<StandardResponse<T>> handleValidationErrors(BindingResult bindingResult) {
        List<String> errorMessages = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        StandardResponse<T> errorResponse = StandardResponse.error("400", "Validation failed", errorMessages);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
