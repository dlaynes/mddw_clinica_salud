package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.EstadoCivil;
import com.grupo2.clinicasalud.model.Genero;
import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.TipoDocumento;
import com.grupo2.clinicasalud.model.form.admin.UsuarioForm;
import com.grupo2.clinicasalud.repository.EspecialidadRepository;
import com.grupo2.clinicasalud.repository.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("generos", Genero.values());
        model.addAttribute("tiposDocumento", TipoDocumento.values());
        model.addAttribute("estadosCiviles", EstadoCivil.values());
        model.addAttribute("listaEspecialidades", especialidadRepository.findAll());
        model.addAttribute("medico", medico);

        return "dashboard/admin/medicos/editar";
    }

    @PostMapping("/editar")
    public String guardar(@Valid @ModelAttribute("medico") Medico medico, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("generos", Genero.values());
            model.addAttribute("tiposDocumento", TipoDocumento.values());
            model.addAttribute("estadosCiviles", EstadoCivil.values());
            model.addAttribute("listaEspecialidades", especialidadRepository.findAll());
            model.addAttribute("medico", medico);
            return "dashboard/admin/medicos/editar";
        }
        medicoRepository.save(medico);
        redirectAttributes.addFlashAttribute("success", "Se han guardado los datos del médico");
        return "redirect:/dashboard/admin/medicos";
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try {
            medicoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successDelete", "Se ha borrado el médico exitosamente");
        } catch(Exception e){
            System.out.println("ERROR BORRAR MEDICO: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorDelete", "No se pudo borrar al médico");
        }
        return "redirect:/dashboard/admin/medicos";
    }

}
