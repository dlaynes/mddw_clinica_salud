package com.grupo2.clinicasalud.model;

public enum TipoDocumento {
    dni('D'),
    carnetExtranjeria('E'),
    pasaporte('P');

    private final char tipo;

    TipoDocumento(char tipo){
        this.tipo = tipo;
    }

    public char getTipo(){
        return this.tipo;
    }
}
