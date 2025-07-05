package com.grupo2.clinicasalud.model.converter;

import com.grupo2.clinicasalud.model.TipoDocumento;
import jakarta.persistence.AttributeConverter;

public class TipoDocumentoAttributeConverter implements AttributeConverter<TipoDocumento, String> {
    @Override
    public String convertToDatabaseColumn(TipoDocumento tipoDocumento) {
        return tipoDocumento != null ? tipoDocumento.toString() : null;
    }

    @Override
    public TipoDocumento convertToEntityAttribute(String s) {
        if(s == null) return null;

        return switch(s){
            case "D" -> TipoDocumento.dni;
            case "P" -> TipoDocumento.pasaporte;
            case "E" -> TipoDocumento.carnetExtranjeria;
            default -> null;
        };
    }
}
