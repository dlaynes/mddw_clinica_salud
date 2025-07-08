package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Rol;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.model.form.LoginForm;
import com.grupo2.clinicasalud.model.form.RegistroForm;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import com.grupo2.clinicasalud.repository.RolRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/auth")
@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private RolRepository roleRepository;

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

        if (usuarioRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("param.error", "El email ya está registrado");
            return "redirect:/auth/registro";
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(registroForm.getPassword()));

        // Asignar rol
        Set<Rol> roles = new HashSet<>();
        Rol clienteRol = roleRepository.findByNombre("Cliente")
                .orElseThrow(() -> new RuntimeException("Error: Rol Cliente no encontrado."));
        roles.add(clienteRol);
        usuario.setRoles(roles);

        Paciente paciente = new Paciente();
        paciente.setNombre(registroForm.getNombre());
        paciente.setApellido(registroForm.getApellidos());
        paciente.setEmail(registroForm.getEmail());
        paciente.setTelefono(registroForm.getTelefono());
        // Es necesario que el registro del paciente tenga un ID antes de asociarlo al usuario actual
        pacienteRepository.save(paciente);

        usuario.setPaciente(paciente);
        usuarioRepository.save(usuario);

        paciente.setUsuario(usuario);
        pacienteRepository.save(paciente);

        redirectAttributes.addFlashAttribute("param:success", "Usuario registrado exitosamente");
        return "redirect:/auth/login";
    }

}
