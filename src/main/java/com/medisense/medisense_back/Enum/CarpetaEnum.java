package com.medisense.medisense_back.Enum;

public enum CarpetaEnum {
    ACADEMICO("academico"),
    OTRO("otro");  // puedes agregar más carpetas aquí

    private final String carpeta;

    CarpetaEnum(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getCarpeta() {
        return carpeta;
    }
}