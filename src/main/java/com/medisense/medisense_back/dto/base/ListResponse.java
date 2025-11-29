package com.medisense.medisense_back.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListResponse<T>(
        int status,
        String message,
        List<T> data,
        Integer totalElements
) {
}
