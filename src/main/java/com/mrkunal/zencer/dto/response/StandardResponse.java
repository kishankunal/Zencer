package com.mrkunal.zencer.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL) // Will exclude fields with null values
public class StandardResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public StandardResponse(boolean success, String code, T data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }
}