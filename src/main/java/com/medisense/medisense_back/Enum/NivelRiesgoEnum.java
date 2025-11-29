package com.medisense.medisense_back.Enum;

public enum NivelRiesgoEnum {
    BAJO("Bajo"),
    MODERADO("Moderado"),
    ALTO("Alto"),
    CRITICO("Crítico");

    private final String descripcion;

    NivelRiesgoEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

