package com.medisense.medisense_back.Enum;

public enum RolEnum {
    ADMIN(1L),
    ESPECIALISTA(2L),
    AGENTE(3L);

    private final Long valor;

    RolEnum(Long valor) {
        this.valor = valor;
    }

    // Getter
    public Long getValor() {
        return valor;
    }

    public static RolEnum fromValor(Long valor) {
        for (RolEnum rol : RolEnum.values()) {
            if (rol.getValor().equals(valor)) {
                return rol;
            }
        }
        return null;
    }
}
