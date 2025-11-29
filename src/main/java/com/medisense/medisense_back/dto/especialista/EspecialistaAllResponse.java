package com.medisense.medisense_back.dto.especialista;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
import com.medisense.medisense_back.dto.usuario.UsuarioAllResponse;
import com.medisense.medisense_back.model.Especialidad;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EspecialistaAllResponse {
    private UsuarioAllResponse usuario;
    private EspecialidadShortResponse especialidad;
    private String nombre;
    private String celular;
    private String dni;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean enabled;
}
