package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.Servicio;
import com.grupo2.clinicasalud.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/servicios")
public class ServiciosController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public String lista(Model model){
        model.addAttribute("servicios", servicioService.dameServicios());
        return "dashboard/servicios/index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        Servicio servicio = new Servicio();
        model.addAttribute("servicio", servicio);
        return "dashboard/servicios/editar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Servicio servicio = servicioService.dameServicioPorId(id);
        if(servicio == null){
            return "redirect:/dashboard/servicios";
        }
        model.addAttribute("servicio", servicio);
        return "dashboard/servicios/editar";
    }

    @PostMapping("/editar")
    public String guardar(@ModelAttribute("servicio") Servicio servicio, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("success", "Se ha guardado el servicio");
        servicioService.guardarServicio(servicio);
        return "redirect:/dashboard/servicios";
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try {
            servicioService.eliminarServicio(id);
            redirectAttributes.addFlashAttribute("successDelete", "Se ha borrado el servicio");
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("errorDelete", "No se pudo borrar el servicio");
        }
        return "redirect:/dashboard/servicios";
    }
}
