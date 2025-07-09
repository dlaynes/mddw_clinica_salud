package com.grupo2.clinicasalud.controller.dashboardMedico;

import com.grupo2.clinicasalud.model.Cita;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.repository.CitaRepository;
import com.grupo2.clinicasalud.repository.MedicoRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/doctor/citas")
public class CitasDoctorController {

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public String index(Model model){
        Usuario usuario = getUser();
        if(usuario == null){
            System.out.println("USUARIO MEDICO NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        List<Cita> citaList = citaRepository.findByMedicoIdOrderByFechaHoraDesc(usuario.getMedico().getId());
        model.addAttribute("citas", citaList);

        return "dashboard/doctor/citas/index";
    }

    private Usuario getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Usuario> user = usuarioRepository.findByEmail(auth.getName());
        return user.orElse(null);
    }

}
