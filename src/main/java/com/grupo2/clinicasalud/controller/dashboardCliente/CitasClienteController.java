package com.grupo2.clinicasalud.controller.dashboardCliente;

import com.grupo2.clinicasalud.model.Cita;
import com.grupo2.clinicasalud.model.EstadoCita;
import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.repository.CitaRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import com.grupo2.clinicasalud.service.auth.DetalleUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/cliente/citas")
public class CitasClienteController {

    @Autowired
    DetalleUsuarioService detalleUsuarioService;

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public String index(Model model){
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("USUARIO PACIENTE NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        List<Cita> citaList = citaRepository.findByPacienteIdOrderByFechaHoraDesc(paciente.getId());
        model.addAttribute("citas", citaList);

        return "dashboard/cliente/citas/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model){
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("USUARIO PACIENTE NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        Optional<Cita> citaOpt = citaRepository.findOneByIdAndPacienteId(id, paciente.getId());
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/cliente/citas";
        }
        Cita cita =  citaOpt.get();
        model.addAttribute("cita", cita);
        return "dashboard/cliente/citas/ver";
    }

    @GetMapping("/ticket/{id}")
    public String ticket(@PathVariable Long id, Model model) {
        Paciente paciente = detalleUsuarioService.getPacienteActual();
        if(paciente == null){
            System.out.println("USUARIO PACIENTE NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        Optional<Cita> citaOpt = citaRepository.findOneByIdAndPacienteIdAndEstadoCita(id, paciente.getId(),EstadoCita.programada);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/cliente/citas";
        }
        Cita cita =  citaOpt.get();
        model.addAttribute("cita", cita);
        return "dashboard/cliente/citas/ticket";
    }

    @GetMapping("/agenda")
    public String agenda(){
        return "dashboard/cliente/citas/agenda";
    }

}
