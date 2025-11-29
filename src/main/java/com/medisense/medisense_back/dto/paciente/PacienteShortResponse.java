package com.medisense.medisense_back.dto.paciente;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PacienteShortResponse {
    private Long idPaciente;
    private String nombres;
    private String apellidos;
    private String email;
    private String celular;
    private Boolean enabled;
}
