package com.medisense.medisense_back.dto.caso;

import com.medisense.medisense_back.Enum.EstadoCasoEnum;
import com.medisense.medisense_back.Enum.NivelRiesgoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CasoCreateRequest {

    @NotNull(message = "El id del paciente es obligatorio.")
    @Positive(message = "El id del paciente debe ser un número válido.")
    private Long idPaciente;

    @NotNull(message = "Debe registrar al menos un síntoma.")
    @Size(min = 1, message = "Debe registrar al menos un síntoma.")
    private List<@NotBlank(message = "El síntoma no puede estar vacío.") String> sintomas;

    @NotNull(message = "Debe indicar el estado del caso.")
    private EstadoCasoEnum estado;

    @NotNull(message = "Debe indicar el nivel de riesgo.")
    private NivelRiesgoEnum riesgo;

    @NotNull(message = "El id de la especialidad es obligatorio.")
    @Positive(message = "El id de la especialidad debe ser un número válido.")
    private Long idEspecialidad;

    @Size(max = 500, message = "La recomendación no debe exceder los 500 caracteres.")
    private String recomendacionIa;
}