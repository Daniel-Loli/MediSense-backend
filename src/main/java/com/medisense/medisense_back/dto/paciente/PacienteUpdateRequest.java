package com.medisense.medisense_back.dto.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteUpdateRequest {
    private String nombres;
    private String apellidos;
    private String email;
    private String celular;
    private String dni;
    private Double peso;
    private Double talla;
}
