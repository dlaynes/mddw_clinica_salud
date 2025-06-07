package com.grupo2.clinicasalud.model.converter;

import com.grupo2.clinicasalud.model.TipoDocumento;
import jakarta.persistence.AttributeConverter;

public class TipoDocumentoAttributeConverter implements AttributeConverter<TipoDocumento, String> {
    @Override
    public String convertToDatabaseColumn(TipoDocumento tipoDocumento) {
        return tipoDocumento.toString();
    }

    @Override
    public TipoDocumento convertToEntityAttribute(String s) {
        return switch(s){
            case "D" -> TipoDocumento.dni;
            case "P" -> TipoDocumento.pasaporte;
            case "E" -> TipoDocumento.carnetExtranjeria;
            default -> throw new IllegalStateException("Unexpected TipoDocumento value: " + s);
        };
    }
}
