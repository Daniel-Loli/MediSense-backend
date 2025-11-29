package com.medisense.medisense_back.dto.paciente;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteCreateRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 60, message = "Los nombres no pueden superar 60 caracteres")
    private String nombres;

    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    private String email;

    @NotBlank(message = "El número de celular es obligatorio")
    @Pattern(regexp = "\\d{9}", message = "El celular debe tener 9 dígitos")
    private String celular;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    private String dni;

    private Double peso;

    private Double talla;
}
