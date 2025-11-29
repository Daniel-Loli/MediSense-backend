package com.medisense.medisense_back.dto.especialidad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EspecialidadUpdateRequest {
    private String nombre;
    private String descripcion;
}
