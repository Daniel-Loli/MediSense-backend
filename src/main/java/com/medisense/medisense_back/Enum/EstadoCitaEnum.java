package com.medisense.medisense_back.Enum;

public enum EstadoCitaEnum {

    PROGRAMADA("Programada"),
    EN_CURSO("En curso"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada"),
    NO_ASISTIO("No asistió");

    private final String descripcion;

    EstadoCitaEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}