package com.grupo2.clinicasalud.controller.dashboardCliente;

import com.grupo2.clinicasalud.model.HistorialMedico;
import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.repository.HistorialMedicoRepository;
import com.grupo2.clinicasalud.repository.RecetaRepository;
import com.grupo2.clinicasalud.service.auth.DetalleUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/cliente/historial")
public class HIstorialClienteController {

    @Autowired
    RecetaRepository recetaRepository;

    @Autowired
    HistorialMedicoRepository historialMedicoRepository;

    @Autowired
    DetalleUsuarioService detalleUsuarioService;

    @GetMapping
    public String index(Model model){
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("ERROR PACIENTE no encontrado");
            return "redirect:/dashboard/index";
        }

        List<HistorialMedico> historialMedicoList = historialMedicoRepository.findByPacienteIdOrderByFechaConsultaDesc(paciente.getId());
        model.addAttribute("historialList", historialMedicoList);

        return "dashboard/cliente/historial/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model){
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("ERROR PACIENTE no encontrado");
            return "redirect:/dashboard/index";
        }

        Optional<HistorialMedico> historialMedicoOptional = historialMedicoRepository.findOneByIdAndPacienteId(id, paciente.getId());
        if(historialMedicoOptional.isEmpty()){
            return "redirect:/dashboard/index";
        }
        model.addAttribute("historial", historialMedicoOptional.get());
        model.addAttribute("recetasList", recetaRepository.findByHistorialMedicoId(id));
        return "dashboard/cliente/historial/ver";
    }


}
