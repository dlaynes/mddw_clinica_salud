package com.grupo2.clinicasalud.model.converter;

import com.grupo2.clinicasalud.model.Genero;
import jakarta.persistence.AttributeConverter;

public class GeneroAttributeConverter implements AttributeConverter<Genero, String> {
    @Override
    public String convertToDatabaseColumn(Genero genero) {
        return genero.toString();
    }

    @Override
    public Genero convertToEntityAttribute(String s) {
        return switch(s) {
            case "M" -> Genero.masculino;
            case "F" -> Genero.femenino;
            case "" -> Genero.otro;
            default -> throw new IllegalStateException("Unexpected Genero value: " + s);
        };
    }
}
