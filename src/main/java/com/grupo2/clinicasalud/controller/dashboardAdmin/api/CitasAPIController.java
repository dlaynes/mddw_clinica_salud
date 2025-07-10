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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
         evento.setTitle("E: " + cita.getEspecialidad().getNombre() +
                 ", P: " + cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido() +
                 ", M: " + cita.getMedico().getNombre() + " " + cita.getMedico().getApellido());
        evento.setStart(cita.getFechaHora());
        evento.setEnd(Date.from(cita.getFechaHora().toInstant().plus(30, ChronoUnit.MINUTES)));
        return evento;
    }

    // Devolvemos los eventos en el formato que entiende el plugin Calendar
    @GetMapping(value = "/de-doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Evento> dameCitasDeDoctor(@PathVariable Long id, @RequestParam(required = false) String start, @RequestParam(required = false) String end){
        LocalDateTime dateStart, dateEnd;

        try {
            if(start != null && end != null){
                DateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                dateStart = LocalDateTime.ofInstant(parser.parse(start).toInstant(), ZoneId.systemDefault());
                dateEnd = LocalDateTime.ofInstant(parser.parse(end).toInstant(), ZoneId.systemDefault());
            } else {
                dateStart = LocalDateTime.now().minusDays(7);
                dateEnd = LocalDateTime.now().plusDays(7);
            }
        } catch (ParseException e) {
            dateStart = LocalDateTime.now().minusDays(7);
            dateEnd = LocalDateTime.now();
            System.out.println("Received invalid date ranges for Cita: " + start + " " + end);
        }

        List<Cita> citas = citaRepository.findByMedicoIdAndFechaHoraBetweenOrderByFechaHoraDesc(id, dateStart, dateEnd);
        return citas.stream().map(CitasAPIController::convertirCitaEnEvento).toList();
    }

    @GetMapping(value = "/de-paciente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Evento> dameCitasDePaciente(@PathVariable Long id, @RequestParam(required = false) String start, @RequestParam(required = false) String end){
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        LocalDateTime dateStart, dateEnd;

        try {
            if(start != null && end != null){
                dateStart = LocalDateTime.ofInstant(parser.parse(start).toInstant(), ZoneId.systemDefault());
                dateEnd = LocalDateTime.ofInstant(parser.parse(end).toInstant(), ZoneId.systemDefault());
            } else {
                dateStart = LocalDateTime.now().minusDays(7);
                dateEnd = LocalDateTime.now().plusDays(7);
            }
        } catch (ParseException e) {
            dateStart = LocalDateTime.now().minusDays(7);
            dateEnd = LocalDateTime.now();
            System.out.println("Received invalid date ranges for Cita: " + start + " " + end);
        }

        List<Cita> citas = citaRepository.findByPacienteIdAndFechaHoraBetweenOrderByFechaHoraDesc(id, dateStart, dateEnd);
        return citas.stream().map(CitasAPIController::convertirCitaEnEvento).toList();
    }
}
