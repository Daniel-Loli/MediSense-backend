package com.medisense.medisense_back.dto.caso;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medisense.medisense_back.Enum.EstadoCasoEnum;
import com.medisense.medisense_back.Enum.NivelRiesgoEnum;
import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
import com.medisense.medisense_back.dto.paciente.PacienteShortResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CasoPShortResponse {
    private Long idCaso;
    private List<String> sintomas;
    private EstadoCasoEnum estado;
    private NivelRiesgoEnum riesgo;
    private EspecialidadShortResponse especialidad;
    private String recomendacionIa;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean enabled;
}
