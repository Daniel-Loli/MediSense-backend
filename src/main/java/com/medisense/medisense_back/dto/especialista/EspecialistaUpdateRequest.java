package com.medisense.medisense_back.dto.especialista;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EspecialistaUpdateRequest {
    private String dni;
    private Long idEspecialidad;
    private String nombres;
    private String celular;
}
