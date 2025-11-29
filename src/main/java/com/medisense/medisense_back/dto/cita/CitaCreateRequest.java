package com.medisense.medisense_back.dto.cita;

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
public class CitaCreateRequest {
    private Long idPaciente;
    private Long idEspecialista;
    private Long idCaso;
    private ZonedDateTime fechaHora;
    private EstadoCitaEnum estado;
    private String notas;
}
