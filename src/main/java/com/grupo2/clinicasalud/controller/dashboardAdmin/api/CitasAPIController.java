package com.grupo2.clinicasalud.controller.dashboardAdmin.api;

import com.grupo2.clinicasalud.model.Cita;
import com.grupo2.clinicasalud.model.api.Evento;
import com.grupo2.clinicasalud.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/dashboard/admin/citas-api")
public class CitasAPIController {

    @Autowired
    CitaRepository citaRepository;

     static Evento convertirCitaEnEvento(Cita cita){
        Evento evento = new Evento();
        // TODO: ofrecer una mejor descripción del evento
        evento.setTitle("Cita");
        evento.setStart(cita.getFechaHora());
        evento.setEnd(Date.from(cita.getFechaHora().toInstant().plus(30, ChronoUnit.MINUTES)));
        return evento;
    }

    // Devolvemos los eventos en el formato que entiende el plugin Calendar
    @GetMapping(value = "/de-doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Evento> dameCitasDeDoctor(@PathVariable Long id, @RequestParam(required = false) String start, @RequestParam(required = false) String end){
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        Date dateStart, dateEnd;

        try {
            dateStart = parser.parse(start);
            dateEnd = parser.parse(end);
        } catch (ParseException e) {
            System.out.println("Received invalid date ranges for Cita: " + start + " " + end);
        }

        List<Cita> citas = citaRepository.findByMedicoIdOrderByFechaHoraDesc(id);
        return citas.stream().map(CitasAPIController::convertirCitaEnEvento).toList();
    }

    @GetMapping(value = "/de-paciente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Evento> dameCitasDePaciente(@PathVariable Long id, @RequestParam(required = false) String start, @RequestParam(required = false) String end){
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        Date dateStart, dateEnd;

        try {
            dateStart = parser.parse(start);
            dateEnd = parser.parse(end);
        } catch (ParseException e) {
            System.out.println("Received invalid date ranges for Cita: " + start + " " + end);
        }

        List<Cita> citas = citaRepository.findByPacienteIdOrderByFechaHoraDesc(id);
        return citas.stream().map(CitasAPIController::convertirCitaEnEvento).toList();
    }
}
