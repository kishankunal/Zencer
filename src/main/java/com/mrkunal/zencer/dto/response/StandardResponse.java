package com.mrkunal.zencer.dto.response;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class StandardResponse<T> {
    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    // Constructor for successful responses
    public StandardResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static class Builder<T> {
        private boolean success;
        private String code;
        private String message;
        private T data;

        public Builder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder<T> code(String code) {
            this.code = code;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public StandardResponse<T> build() {
            return new StandardResponse<>(success, code, message, data);
        }
    }


    public static <T> StandardResponse<T> handleException(String code, String message, Exception ex) {
        return new StandardResponse<>(false, code,  message, (T)ex.getMessage());
    }
}
