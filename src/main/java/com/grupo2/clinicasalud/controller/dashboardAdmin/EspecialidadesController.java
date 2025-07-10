package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.Especialidad;
import com.grupo2.clinicasalud.service.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/admin/especialidades")
public class EspecialidadesController {

    @Autowired
    private EspecialidadService especialidadesService;

    // Mejora: agregar una columna # de médicos, que funcione como enlace al CRUD de médicos
    @GetMapping
    public String lista(Model model){
        model.addAttribute("especialidades", especialidadesService.dameEspecialidades());
        return "dashboard/admin/especialidades/index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        Especialidad especialidad = new Especialidad();
        model.addAttribute("especialidad", especialidad);
        return "dashboard/admin/especialidades/editar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Especialidad especialidad = especialidadesService.dameEspecialidadPorId(id);
        if(especialidad == null){
            return "redirect:/dashboard/admin/especialidades";
        }
        model.addAttribute("especialidad", especialidad);
        return "dashboard/admin/especialidades/editar";
    }

    @PostMapping("/editar")
    public String guardar(@Valid @ModelAttribute("especialidad") Especialidad especialidad, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if(result.hasErrors()){
            model.addAttribute("especialidad", especialidad);
            return "dashboard/admin/especialidades/editar";
        }

        redirectAttributes.addFlashAttribute("success", "Se ha guardado la especialidad");
        especialidadesService.guardarEspecialidad(especialidad);
        return "redirect:/dashboard/admin/especialidades";
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try {
            especialidadesService.eliminarEspecialidad(id);
            redirectAttributes.addFlashAttribute("successDelete", "Se ha borrado la especialidad");
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("errorDelete", "No se pudo borrar la especialidad");
        }
        return "redirect:/dashboard/admin/especialidades";
    }
}
