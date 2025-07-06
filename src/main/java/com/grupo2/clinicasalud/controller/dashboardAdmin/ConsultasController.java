package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.Consulta;
import com.grupo2.clinicasalud.model.Especialidad;
import com.grupo2.clinicasalud.model.Servicio;
import com.grupo2.clinicasalud.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/admin/consultas")
public class ConsultasController {

    @Autowired
    ConsultaService consultaService;

    @GetMapping
    public String lista(Model model){
        model.addAttribute("consultas", consultaService.dameConsultas());
        return "dashboard/admin/consultas/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model){
        Consulta consulta = consultaService.dameConsultaPorId(id);
        if(consulta == null){
            return "redirect:/dashboard/consultas";
        }
        model.addAttribute("consulta", consulta);
        return "dashboard/admin/consultas/ver";
    }

    // Mejora: envio de respuestas

}
