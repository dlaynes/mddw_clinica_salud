package com.grupo2.clinicasalud.model;

public enum EstadoCita {
    registrada, // Cuando la cita es creada por un visitante
    rechazada, // Cuando se rechaza una cita creada por un visitante
    programada, // Cuando se acepta una cita creada por un visitante o es llenada desde el panel de control
    completada, // Cuando se concluye una cita
    cancelada, // Cuando se cancela una cita programada
    enEspera; // Cuando el cliente asiste a una cita, o se encuentra en proceso

    @Override
    public String toString() {
        return switch (this){
            case registrada -> "Registrada";
            case rechazada -> "Rechazada";
            case programada -> "Programada";
            case cancelada -> "Cancelada";
            case enEspera -> "En espera";
            case completada -> "Completada";
            default -> "Desconocido";
        };
    }

    public String toText(){
        return switch (this) {
            case registrada -> "Registrada";
            case rechazada -> "Rechazada";
            case programada -> "Programada";
            case cancelada -> "Cancelada";
            case enEspera -> "En espera";
            case completada -> "Completada";
            default -> "";
        };
    };

    public String toBootstrapColor(){
        return switch (this) {
            case registrada -> "secondary";
            case rechazada -> "warning";
            case programada -> "info";
            case cancelada -> "danger";
            case enEspera -> "success";
            case completada -> "primary";
            default -> "light";
        };
    }

}
