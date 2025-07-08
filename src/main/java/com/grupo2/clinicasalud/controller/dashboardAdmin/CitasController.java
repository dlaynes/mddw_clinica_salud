package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.Cita;
import com.grupo2.clinicasalud.model.Consulta;
import com.grupo2.clinicasalud.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/dashboard/admin/citas")
public class CitasController {

    @Autowired
    CitaRepository citaRepository;

    @GetMapping
    public String index(Model model){
        List<Cita> citaList = citaRepository.findAll();
        model.addAttribute("citas", citaList);

        return "dashboard/admin/citas/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model){
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/admin/citas";
        }
        model.addAttribute("cita", citaOpt.get());
        return "dashboard/admin/citas/ver";
    }
}
