package com.grupo2.clinicasalud.model;

public enum EstadoCita {
    programada("Programada"),
    completada("Completada"),
    cancelada("Cancelada"),
    enEspera("En espera");

    private final String estado;

    EstadoCita(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return this.estado;
    }
}
