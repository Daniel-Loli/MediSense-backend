package com.medisense.medisense_back.dto.caso;

import com.medisense.medisense_back.Enum.EstadoCasoEnum;
import com.medisense.medisense_back.Enum.NivelRiesgoEnum;
import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
import com.medisense.medisense_back.dto.paciente.PacienteShortResponse;

import java.time.ZonedDateTime;
import java.util.List;

public class CasoEShortResponse {
    private Long idCaso;
    private PacienteShortResponse paciente;
    private List<String> sintomas;
    private EstadoCasoEnum estado;
    private NivelRiesgoEnum riesgo;
    private String recomendacionIa;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean enabled;
}
