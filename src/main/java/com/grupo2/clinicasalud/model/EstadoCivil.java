package com.grupo2.clinicasalud.model;

public enum EstadoCivil {
    casado,
    soltero,
    viudo,
    divorciado;

    public String toString(){
        return switch (this) {
            case casado -> "C";
            case soltero -> "S";
            case viudo -> "V";
            case divorciado -> "D";
            default -> "Desconocido";
        };
    }
}
