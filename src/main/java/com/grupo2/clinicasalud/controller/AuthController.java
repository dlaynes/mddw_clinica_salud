package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.model.form.LoginForm;
import com.grupo2.clinicasalud.model.form.RegistroForm;
import com.grupo2.clinicasalud.service.PacienteService;
import com.grupo2.clinicasalud.service.auth.DetalleUsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/auth")
@Controller
public class AuthController {

    @Autowired
    private DetalleUsuarioService detalleUsuarioService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(required = false) String register, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) {
        if(register != null){
           model.addAttribute("successText", "Su cuenta ha sido creada satisfactoriamente. Ya puede ingresar al sistema");
        }
        if(error != null){
            model.addAttribute("loginError", "Error");
        }
        if(logout != null){
            model.addAttribute("logoutText", "Sesión cerrada");
        }
        model.addAttribute("login", new LoginForm());
        return "auth/login";
    }

    @GetMapping("/registro")
    public String registerPage(Model model) {
        model.addAttribute("usuario", new RegistroForm());
        return "auth/registro";
    }

    @Transactional
    @PostMapping("/registro")
    public String registerUser(@Valid @ModelAttribute("usuario") RegistroForm registroForm, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            return "redirect:/auth/registro";
        }

        // Validaciones
        if (!registroForm.getPassword().equals(registroForm.getPasswordConfirm())) {
            redirectAttributes.addFlashAttribute("param.error", "Las contraseñas no coinciden");
            return "redirect:/auth/registro";
        }

        String email = registroForm.getEmail().toLowerCase();

        if (detalleUsuarioService.existeConCorreo(email)) {
            redirectAttributes.addFlashAttribute("param.error", "El email ya está registrado");
            return "redirect:/auth/registro";
        }

        Usuario usuario = detalleUsuarioService.guardarCliente(email, registroForm.getPassword(), passwordEncoder);

        Paciente paciente = pacienteService.guardarPaciente(
                registroForm.getNombre(),
                registroForm.getApellidos(),
                registroForm.getEmail(),
                registroForm.getTelefono(),
                usuario.getId()
        );

        usuario.setPacienteId(paciente.getId());
        detalleUsuarioService.guardarUsuario(usuario);

        redirectAttributes.addFlashAttribute("param:success", "Usuario registrado exitosamente");
        return "redirect:/auth/login";
    }

}
