package com.grupo2.clinicasalud.model;

public enum EstadoCita {
    programada,
    completada,
    cancelada,
    enEspera;

    @Override
    public String toString() {
        return switch (this){
            case programada -> "Programada";
            case cancelada -> "Cancelada";
            case enEspera -> "En espera";
            case completada -> "Completada";
            default -> "Desconocido";
        };
    }
}
