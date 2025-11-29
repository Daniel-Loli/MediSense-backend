package com.medisense.medisense_back.dto.rol;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medisense.medisense_back.Enum.RolEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolAllResponse {
    private Long idRol;
    private RolEnum nombre;
}
