package com.medisense.medisense_back.dto.caso;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medisense.medisense_back.Enum.EstadoCasoEnum;
import com.medisense.medisense_back.Enum.NivelRiesgoEnum;
import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
import com.medisense.medisense_back.dto.paciente.PacienteShortResponse;
import com.medisense.medisense_back.model.Especialidad;
import com.medisense.medisense_back.model.Paciente;
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
public class CasoShortResponse {
    private Long idCaso;
    private PacienteShortResponse paciente;
    private EstadoCasoEnum estado;
    private NivelRiesgoEnum riesgo;
    private EspecialidadShortResponse especialidad;
    private ZonedDateTime updatedAt;
    private Boolean enabled;
}
