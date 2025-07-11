package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.form.admin.UsuarioForm;
import com.grupo2.clinicasalud.repository.CitaRepository;
import com.grupo2.clinicasalud.repository.ConsultorioRepository;
import com.grupo2.clinicasalud.repository.EspecialidadRepository;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/admin/pacientes")
public class PacientesController {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    ConsultorioRepository consultorioRepository;

    @GetMapping
    public String index(Model model){

        model.addAttribute("pacientes", pacienteRepository.findAll());
        return "dashboard/admin/pacientes/index";
    }

    // Los pacientes se crean desde el editor de usuarios

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if(pacienteOpt.isEmpty()){
            return "redirect:/dashboard/admin/pacientes";
        }
        Paciente paciente = pacienteOpt.get();
        model.addAttribute("generos", Genero.values());
        model.addAttribute("tiposDocumento", TipoDocumento.values());
        model.addAttribute("estadosCiviles", EstadoCivil.values());
        model.addAttribute("paciente", paciente);

        return "dashboard/admin/pacientes/editar";
    }

    @PostMapping("/editar")
    public String guardar(@Valid @ModelAttribute("paciente") Paciente paciente, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("generos", Genero.values());
            model.addAttribute("tiposDocumento", TipoDocumento.values());
            model.addAttribute("estadosCiviles", EstadoCivil.values());
            model.addAttribute("paciente", paciente);
            return "dashboard/admin/pacientes/editar";
        }
        pacienteRepository.save(paciente);
        redirectAttributes.addFlashAttribute("success", "Se han guardado los datos del médico");
        return "redirect:/dashboard/admin/pacientes";
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try {
            pacienteRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successDelete", "Se ha borrado el paciente exitosamente");
        } catch(Exception e){
            System.out.println("ERROR BORRAR PACIENTE: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorDelete", "No se pudo borrar al paciente");
        }
        return "redirect:/dashboard/admin/pacientes";
    }

    @GetMapping("/crear-cita/{id}")
    public String crearCita(@PathVariable Long id, Model model){
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if(pacienteOpt.isEmpty()){
            return "redirect:/dashboard/admin/pacientes";
        }
        Cita cita = new Cita();
        Paciente paciente = pacienteOpt.get();
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("paciente", paciente);
        model.addAttribute("cita", cita);
        return "dashboard/admin/pacientes/crear-cita";
    }

    @PostMapping("/crear-cita/{id}")
    private String nuevaCita(
            @PathVariable Long id,
            @Valid @ModelAttribute("cita") Cita cita,
            BindingResult result,
            Model model,
            RedirectAttributes attributes
    ){
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if(pacienteOpt.isEmpty()){
            return "redirect:/dashboard/admin/pacientes";
        }
        if(result.hasErrors()){
            model.addAttribute("cita", cita);
            model.addAttribute("especialidades", especialidadRepository.findAll());
            return "dashboard/admin/pacientes/crear-cita";
        }
        Paciente paciente = pacienteOpt.get();

        Optional<Consultorio> consultorioOptional = consultorioRepository.findById(1L);
        if(consultorioOptional.isEmpty()){
            throw new RuntimeException("Error: No se encontró un consultorio.");
        }

        if(cita.getEspecialidad() == null){
            model.addAttribute("citaError", "Debe seleccionar una especialidad");
            model.addAttribute("especialidades", especialidadRepository.findAll());
            model.addAttribute("cita", cita);
            return "dashboard/admin/pacientes/crear-cita";
        }

        cita.setEstadoCita(EstadoCita.registrada);
        cita.setPaciente(paciente);
        cita.setConsultorio(consultorioOptional.get());
        cita.setFechaRegistro(LocalDateTime.now());
        citaRepository.save(cita);

        attributes.addFlashAttribute("crearSuccess", "Se creó la cita");
        return "redirect:/dashboard/admin/citas";
    }

}
