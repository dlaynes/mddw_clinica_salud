package com.grupo2.clinicasalud.model;

public enum TipoDocumento {
    dni,
    carnetExtranjeria,
    pasaporte;

    @Override
    public String toString() {
        return switch (this) {
            case dni -> "D";
            case carnetExtranjeria -> "E";
            case pasaporte -> "P";
            default -> "";
        };
    }
}
