package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.form.admin.UsuarioForm;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/dashboard/admin/pacientes")
public class PacientesController {

    @Autowired
    PacienteRepository pacienteRepository;

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

}
