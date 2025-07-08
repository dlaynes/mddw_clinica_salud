package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.form.admin.UsuarioForm;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
