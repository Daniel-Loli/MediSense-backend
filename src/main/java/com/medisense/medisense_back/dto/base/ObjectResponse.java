package com.medisense.medisense_back.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ObjectResponse<T>(
        int status,
        String message,
        T data
) {
}
