package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.repository.EspecialidadRepository;
import com.grupo2.clinicasalud.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/dashboard/admin/medicos")
public class MedicosController {

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @GetMapping
    public String index(Model model){

        model.addAttribute("medicos", medicoRepository.findAll());
        return "dashboard/admin/medicos/index";
    }

    // Los médicos se crean desde el editor de usuarios

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Optional<Medico> medicoOptional = medicoRepository.findById(id);
        if(medicoOptional.isEmpty()){
            return "redirect:/dashboard/admin/medicos";
        }
        Medico medico = medicoOptional.get();

        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("medico", medico);

        return "dashboard/admin/medicos/editar";
    }

}
