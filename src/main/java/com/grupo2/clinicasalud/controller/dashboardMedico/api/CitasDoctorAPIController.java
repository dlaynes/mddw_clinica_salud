package com.grupo2.clinicasalud.controller.dashboardMedico.api;

import com.grupo2.clinicasalud.controller.dashboardCliente.api.CitasClienteAPIController;
import com.grupo2.clinicasalud.model.Cita;
import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.api.Evento;
import com.grupo2.clinicasalud.repository.CitaRepository;
import com.grupo2.clinicasalud.service.auth.DetalleUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/dashboard/doctor/citas-api")
@RestController
public class CitasDoctorAPIController {

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    DetalleUsuarioService detalleUsuarioService;

    static Evento convertirCitaEnEvento(Cita cita){
        Evento evento = new Evento();
        evento.setTitle("E: " + cita.getEspecialidad().getNombre() +
                ", P: " + cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido());
        evento.setStart(cita.getFechaHora());
        evento.setEnd(Date.from(cita.getFechaHora().toInstant().plus(30, ChronoUnit.MINUTES)));
        return evento;
    }

    // Devolvemos los eventos en el formato que entiende el plugin Calendar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Evento> dameCitas(@RequestParam(required = false) String start, @RequestParam(required = false) String end){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("ERROR PACIENTE no encontrado");
            return new ArrayList<>();
        }

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
            dateEnd = LocalDateTime.now().plusDays(7);
            System.out.println("Received invalid date ranges for Cita: " + start + " " + end);
        }

        List<Cita> citas = citaRepository.findByMedicoIdAndFechaHoraBetweenOrderByFechaHoraDesc(medico.getId(), dateStart, dateEnd);
        return citas.stream().map(CitasDoctorAPIController::convertirCitaEnEvento).toList();
    }

}
