package com.medisense.medisense_back.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
import com.medisense.medisense_back.dto.usuario.UsuarioAllResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EspecialistaAuthResponse {
    private EspecialidadShortResponse especialidad;
    private String nombre;
    private String telefono;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean enabled;
}
