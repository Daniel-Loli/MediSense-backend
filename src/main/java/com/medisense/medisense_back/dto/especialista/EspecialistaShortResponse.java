package com.medisense.medisense_back.dto.especialista;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
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
public class EspecialistaShortResponse {
    private EspecialidadShortResponse especialidad;
    private String nombres;
    private String celular;
    private Boolean enabled;
}
