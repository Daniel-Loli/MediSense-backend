package com.medisense.medisense_back.Enum;

public enum EstadoCasoEnum {

    NUEVO("Nuevo"),                         // Caso recién creado
    EN_EVALUACION("En evaluación"),          // IA o profesional evaluando
    PENDIENTE_ATENCION("Pendiente de atención"), // Ya aprobado para consulta
    ATENDIDO("Atendido"),                    // Caso ya atendido por especialista
    CERRADO("Cerrado"),                      // Caso finalizado sin seguimiento
    CANCELADO("Cancelado");                  // Paciente canceló o no asistió

    private final String descripcion;

    EstadoCasoEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}