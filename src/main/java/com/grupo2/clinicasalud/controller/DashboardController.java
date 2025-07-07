package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.EstadoCita;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.service.CitaService;
import com.grupo2.clinicasalud.service.MedicoService;
import com.grupo2.clinicasalud.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/index")
    public String index(Model model, @AuthenticationPrincipal Usuario user){
        if(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Admin"))){
            return indexAdmin(model);
        }
        if(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Doctor"))){
            return indexMedicos(model);
        }
        if(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Cliente"))){
            return indexPacientes(model);
        }
        throw new RuntimeException("Unknown role");
    }

    private String indexAdmin(Model model){
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        model.addAttribute("citasHoy", citaService.countCitasBetween(startOfDay, endOfDay));
        model.addAttribute("totalPacientes", pacienteService.countPacientes());
        model.addAttribute("totalMedicos", medicoService.countMedicos());
        model.addAttribute("citasPendientes", citaService.countCitasByEstado(EstadoCita.registrada));

        // Obtener próximas citas (hoy + 7 días)
        LocalDateTime endOfWeek = endOfDay.plusDays(7);
        model.addAttribute("proximasCitas", citaService.findCitasBetween(startOfDay, endOfWeek));

        return "dashboard/index";
    }

    private String indexMedicos(Model model){
        return "dashboard/index_medicos";
    }

    private String indexPacientes(Model model){
        return "dashboard/index_pacientes";
    }

}
