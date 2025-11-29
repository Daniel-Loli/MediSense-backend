package com.medisense.medisense_back.dto.cita;

import com.medisense.medisense_back.Enum.EstadoCitaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitaUpdateRequest {
    private Long idEspecialista;
    private ZonedDateTime fechaHora;
    private EstadoCitaEnum estado;
    private String notas;
}
