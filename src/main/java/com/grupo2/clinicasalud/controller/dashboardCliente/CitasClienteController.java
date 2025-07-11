package com.grupo2.clinicasalud.controller.dashboardCliente;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.repository.CitaRepository;
import com.grupo2.clinicasalud.repository.ConsultorioRepository;
import com.grupo2.clinicasalud.repository.EspecialidadRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import com.grupo2.clinicasalud.service.auth.DetalleUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/cliente/citas")
public class CitasClienteController {

    @Autowired
    DetalleUsuarioService detalleUsuarioService;

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    ConsultorioRepository consultorioRepository;

    @Autowired
    EspecialidadRepository especialidadRepository;

    @GetMapping
    public String index(Model model){
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("USUARIO PACIENTE NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        List<Cita> citaList = citaRepository.findByPacienteIdOrderByFechaHoraDesc(paciente.getId());
        model.addAttribute("citas", citaList);

        return "dashboard/cliente/citas/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model){
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("USUARIO PACIENTE NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        Optional<Cita> citaOpt = citaRepository.findOneByIdAndPacienteId(id, paciente.getId());
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/cliente/citas";
        }
        Cita cita =  citaOpt.get();
        model.addAttribute("cita", cita);
        return "dashboard/cliente/citas/ver";
    }

    @GetMapping("/ticket/{id}")
    public String ticket(@PathVariable Long id, Model model) {
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("USUARIO PACIENTE NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        Optional<Cita> citaOpt = citaRepository.findOneByIdAndPacienteIdAndEstadoCita(id, paciente.getId(),EstadoCita.programada);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/cliente/citas";
        }
        Cita cita =  citaOpt.get();
        model.addAttribute("cita", cita);
        return "dashboard/cliente/citas/ticket";
    }

    // Un cliente no puede cancelar citas En espera, para evitar problemas
    // con la logística del hospital
    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, RedirectAttributes attributes){
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("USUARIO PACIENTE NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        Optional<Cita> citaOpt = citaRepository.findOneByIdAndEstadoCita(id, EstadoCita.programada);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/cliente/citas";
        }
        Cita cita = citaOpt.get();
        if(cita.getPaciente().getId() != paciente.getId()){
            return "redirect:/dashboard/cliente/citas";
        }

        cita.setEstadoCita(EstadoCita.cancelada);
        citaRepository.save(cita);

        attributes.addFlashAttribute("cancelarSuccess", "Se canceló la cita");
        return "redirect:/dashboard/cliente/citas";
    }

    @GetMapping("/crear")
    private String nuevaCita(Model model){
        Cita cita = new Cita();
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("cita", cita);
        return "dashboard/cliente/citas/crear";
    }

    @PostMapping("/crear")
    private String crearCita(
            @Valid @ModelAttribute("cita") Cita cita,
            BindingResult result,
            Model model,
            RedirectAttributes attributes
            ){
        if(result.hasErrors()){
            model.addAttribute("cita", cita);
            model.addAttribute("especialidades", especialidadRepository.findAll());
            return "dashboard/cliente/citas/crear";
        }
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("USUARIO PACIENTE NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }
        Optional<Consultorio> consultorioOptional = consultorioRepository.findById(1L);
        if(consultorioOptional.isEmpty()){
            throw new RuntimeException("Error: No se encontró un consultorio.");
        }

        if(cita.getEspecialidad() == null){
            model.addAttribute("citaError", "Debe seleccionar una especialidad");
            model.addAttribute("especialidades", especialidadRepository.findAll());
            model.addAttribute("cita", cita);
            return "dashboard/cliente/citas/crear";
        }

        if(!habilitadoParaCrearCitas(paciente.getId(), cita.getEspecialidad())){
            model.addAttribute("citaError", "Ya cuenta con una cita en espera/registrada/programada en la especialidad seleccionada");
            model.addAttribute("cita", cita);
            model.addAttribute("especialidades", especialidadRepository.findAll());
            return "dashboard/cliente/citas/crear";
        }

        cita.setEstadoCita(EstadoCita.registrada);
        cita.setPaciente(paciente);
        cita.setConsultorio(consultorioOptional.get());
        cita.setFechaRegistro(LocalDateTime.now());
        citaRepository.save(cita);

        attributes.addFlashAttribute("crearSuccess", "Se creó la cita");
        return "redirect:/dashboard/cliente/citas";
    }

    private boolean habilitadoParaCrearCitas(Long pacienteId, Especialidad especialidad){
        List<EstadoCita> estadoCitasInvalidads = new ArrayList<>();
        estadoCitasInvalidads.add(EstadoCita.registrada);
        estadoCitasInvalidads.add(EstadoCita.programada);
        estadoCitasInvalidads.add(EstadoCita.enEspera);

        return citaRepository.
                countByPacienteIdAndEspecialidadAndEstadoCitaIn(pacienteId, especialidad, estadoCitasInvalidads) == 0;
    }

}
