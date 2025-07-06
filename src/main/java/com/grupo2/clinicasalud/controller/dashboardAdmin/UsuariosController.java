package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.Servicio;
import com.grupo2.clinicasalud.model.form.admin.UsuarioForm;
import com.grupo2.clinicasalud.repository.MedicoRepository;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import com.grupo2.clinicasalud.repository.RolRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import com.grupo2.clinicasalud.service.transactions.UsuarioTransaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/admin/usuarios")
public class UsuariosController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    UsuarioTransaction usuarioTransaction;

    @GetMapping
    public String index(Model model){
        model.addAttribute("roles", rolRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "dashboard/admin/usuarios/index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        UsuarioForm usuarioForm = new UsuarioForm();
        model.addAttribute("roles", rolRepository.findAll());
        model.addAttribute("usuarioForm", usuarioForm);
        return "dashboard/admin/usuarios/editar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){

        return "dashboard/admin/usuarios/editar";
    }


    @PostMapping("/editar")
    public String guardar(@Valid @ModelAttribute("usuarioForm") UsuarioForm usuarioForm, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuarioForm", usuarioForm);
            return "dashboard/admin/usuarios/editar";
        }

        try {
            usuarioTransaction.guardarUsuario(usuarioForm, passwordEncoder, usuarioRepository, pacienteRepository, medicoRepository);
            redirectAttributes.addFlashAttribute("success", "Se han guardado los datos del usuario");
        } catch(Exception e){

        }

        return "redirect:/dashboard/admin/usuarios";
    }
}
