package com.grupo2.clinicasalud.model;

public enum EstadoCivil {
    casado('C'),
    soltero('S'),
    viudo('V'),
    divorciado('D');

    private final char tipo;

    EstadoCivil(char tipo){
        this.tipo = tipo;
    }

    public char getTipo(){
        return this.tipo;
    }
}
