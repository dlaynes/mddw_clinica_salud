package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.Rol;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.repository.RolRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private RolRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "auth/login";
    }

    @GetMapping("/registro")
    public String registerPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registerUser(@ModelAttribute Usuario usuario,
                               @RequestParam String confirmPassword,
                               @RequestParam String roleType,
                               RedirectAttributes redirectAttributes) {

        // Validaciones
        if (!usuario.getPassword().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/auth/registro";
        }

        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya existe");
            return "redirect:/auth/registro";
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "El email ya está registrado");
            return "redirect:/auth/registro";
        }

        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar rol
        Set<Rol> roles = new HashSet<>();
        switch (roleType) {
            case "cliente":
                Rol clienteRol = roleRepository.findByNombre("Cliente")
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                roles.add(clienteRol);
            default:
                 Rol defaultRol = roleRepository.findByNombre("Visitante")
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                roles.add(defaultRol);
        }

        usuario.setRoles(roles);
        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("success", "Usuario registrado exitosamente");
        return "redirect:/login";
    }

}
