package com.medisense.medisense_back.dto.especialidad;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EspecialidadCreateRequest {
    @NotBlank(message = "El nombre es obligatorios")
    private String nombre;
    private String descripcion;
}
