package com.grupo2.clinicasalud.model.converter;

import com.grupo2.clinicasalud.model.EstadoCita;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EstadoCitaAttributeConverter implements AttributeConverter<EstadoCita,String> {
    @Override
    public String convertToDatabaseColumn(EstadoCita e) {
        return e != null ? e.toString() : null;
    }

    @Override
    public EstadoCita convertToEntityAttribute(String e) {
        if(e == null) return null;

        return switch (e){
            case "Registrada" -> EstadoCita.registrada;
            case "Rechazada" -> EstadoCita.rechazada;
            case "Cancelada" -> EstadoCita.cancelada;
            case "Completada" -> EstadoCita.completada;
            case "En espera" -> EstadoCita.enEspera;
            case "Programada" -> EstadoCita.programada;
            default -> null;
        };
    }
}
