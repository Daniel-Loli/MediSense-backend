package com.medisense.medisense_back.Enum;

public enum Modulo {

    AUTH("auth"),
    USUARIO("usuario"),
    ROL("rol"),
    MATERIAL("material"),
    ESPECIALISTA("especialista"),
    ESPECIALIDAD("especialidad"),
    PACIENTE("Paciente"),
    CASO("caso"),
    CITA("cita");


    private final String name;

    Modulo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // CREACIÓN (201 CREATED)
    public String registrado() {
        return name + " registrado(a) exitosamente";
    }

    // ACTUALIZACIÓN (200 OK)
    public String actualizado() {
        return name + " actualizado(a) exitosamente";
    }

    // ELIMINACIÓN (200 OK)
    public String eliminado() {
        return name + " eliminado(a) exitosamente";
    }

    // ENCONTRADO (200 OK)
    public String encontrado() {
        return name + " encontrado(a)";
    }

    // NO ENCONTRADO (404 NOT_FOUND)
    public String noEncontrado() {
        return name + " no encontrado(a)";
    }

    // LISTADO (200 OK)
    public String listado() {
        return "Lista de " + name + "(s) obtenido(a) exitosamente";
    }

    // LISTADO (200 OK)
    public String listadoPage() {
        return "Lista de " + name + "(s) paginado obtenido(a) exitosamente";
    }

    public String contar() {
        return "Conteo de " + name + "(s) obtenido(a) exitosamente";
    }
    public String duplicado(){
        return name+" ya exitente";
    }

    // RESUMEN DE REGISTROS (200 OK)
    public String resumenAllRegistro(int exitosos, int fallidos) {
        return String.format("%s(s) registrados: %d. Fallidos: %d.", name, exitosos, fallidos);
    }

    // RESUMEN DE ELIMINACIONES (200 OK)
    public String resumenAllEliminacion(int exitosos, int fallidos) {
        return String.format("%s(s) eliminados: %d. Fallidos: %d.", name, exitosos, fallidos);
    }
}
