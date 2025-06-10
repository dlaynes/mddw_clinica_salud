package com.grupo2.clinicasalud.model;

public enum Genero {
    masculino,
    femenino,
    otro;

    public String toText(){
        return switch (this) {
            case masculino -> "Masculino";
            case femenino -> "Femenino";
            case otro -> "Otro";
            default -> "";
        };
    };

    public String toString(){
        return switch (this) {
            case masculino -> "M";
            case femenino -> "F";
            case otro -> "O";
            default -> "";
        };
    };
}
