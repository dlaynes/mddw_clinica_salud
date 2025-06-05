package com.grupo2.clinicasalud.model;

public enum Genero {
    masculino('M'),
    femenino('F'),
    otro('O');

    private final char tipo;

    Genero(char tipo){
        this.tipo = tipo;
    }

    public char getTipo(){
        return this.tipo;
    }

}
