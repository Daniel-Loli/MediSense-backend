package com.medisense.medisense_back.dto.cita;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medisense.medisense_back.Enum.EstadoCitaEnum;
import com.medisense.medisense_back.dto.caso.CasoShortResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaShortResponse;
import com.medisense.medisense_back.dto.paciente.PacienteShortResponse;
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
public class CitaEShortResponse {
    private Long idCita;
    private PacienteShortResponse paciente;
    private CasoShortResponse caso;
    private ZonedDateTime fechaHora;
    private EstadoCitaEnum estado;
    private String notas;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean enabled;
}
