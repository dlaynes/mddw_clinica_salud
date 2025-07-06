package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.Servicio;
import com.grupo2.clinicasalud.service.ServicioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/admin/servicios")
public class ServiciosController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public String lista(Model model){
        model.addAttribute("servicios", servicioService.dameServicios());
        return "dashboard/admin/servicios/index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        Servicio servicio = new Servicio();
        model.addAttribute("servicio", servicio);
        return "dashboard/admin/servicios/editar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Servicio servicio = servicioService.dameServicioPorId(id);
        if(servicio == null){
            return "redirect:/dashboard/admin/servicios";
        }
        model.addAttribute("servicio", servicio);
        return "dashboard/admin/servicios/editar";
    }

    @PostMapping("/editar")
    public String guardar(@Valid @ModelAttribute("servicio") Servicio servicio, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if(result.hasErrors()){
            model.addAttribute("servicio", servicio);
            return "dashboard/admin/servicios/editar";
        }

        redirectAttributes.addFlashAttribute("success", "Se ha guardado el servicio");
        servicioService.guardarServicio(servicio);
        return "redirect:/dashboard/admin/servicios";
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try {
            servicioService.eliminarServicio(id);
            redirectAttributes.addFlashAttribute("successDelete", "Se ha borrado el servicio");
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("errorDelete", "No se pudo borrar el servicio");
        }
        return "redirect:/dashboard/admin/servicios";
    }
}
