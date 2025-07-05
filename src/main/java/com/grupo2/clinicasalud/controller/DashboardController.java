package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.service.CitaService;
import com.grupo2.clinicasalud.service.ConsultorioService;
import com.grupo2.clinicasalud.service.MedicoService;
import com.grupo2.clinicasalud.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/dashboard")
@Configuration
public class DashboardController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ConsultorioService consultorioService;

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/index")
    public String index(Model model){

        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        model.addAttribute("citasHoy", citaService.countCitasBetween(startOfDay, endOfDay));
        model.addAttribute("totalPacientes", pacienteService.countPacientes());
        model.addAttribute("totalMedicos", medicoService.countMedicos());
        model.addAttribute("totalConsultorios", consultorioService.countConsultorios());

        // Obtener próximas citas (hoy + 7 días)
        LocalDateTime endOfWeek = endOfDay.plusDays(7);
        model.addAttribute("proximasCitas", citaService.findCitasBetween(startOfDay, endOfWeek));

        return "dashboard/index";
    }
}
