package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.Usuario;
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

import java.util.Optional;

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
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if(usuarioOpt.isEmpty()){
            return "redirect:/dashboard/admin/usuarios";
        }
        Usuario usuario = usuarioOpt.get();

        UsuarioForm usuarioForm = new UsuarioForm();
        usuarioForm.setId(usuario.getId());
        usuarioForm.setEmail(usuario.getEmail());
        usuarioForm.setRoles(usuario.getRoles());

        model.addAttribute("roles", rolRepository.findAll());
        model.addAttribute("usuarioForm", usuarioForm);

        return "dashboard/admin/usuarios/editar";
    }

    @PostMapping("/editar")
    public String guardar(@Valid @ModelAttribute("usuarioForm") UsuarioForm usuarioForm, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuarioId", usuarioForm.getId());
            model.addAttribute("roles", rolRepository.findAll());
            model.addAttribute("usuarioForm", usuarioForm);
            return "dashboard/admin/usuarios/editar";
        }

        // evitar que 2 usuarios compartan el mismo email
        Usuario usuario;
        Optional<Usuario> usuarioAnt = usuarioRepository.findByEmail(usuarioForm.getEmail());
        if(usuarioAnt.isPresent()){
            Long id = usuarioForm.getId();

            usuario = usuarioAnt.get();
            if(id == null || (usuario.getId() != id)){
                model.addAttribute("errorUsuario", "Ya existe un usuario con el mismo e-mail");
                model.addAttribute("usuarioId", usuarioForm.getId());
                model.addAttribute("roles", rolRepository.findAll());
                model.addAttribute("usuarioForm", usuarioForm);
                return "dashboard/admin/usuarios/editar";
            }
        } else {
            usuario = new Usuario();
        }

        try {
            // Debido a que las tablas Medico y Paciente guardan la misma información
            // y otros roles no participan, se producen procesos repetitivos e inconexos dentro de este servicio/método
            // Lo ideal hubiese sido crear una tabla Perfil, o guardar la información de Usuario
            // en su propia tabla, omitiendo las tablas de pacientes y médicos.
            // Pero esas soluciones involucran escribir consultas con JOINs para pedir algunos datos del portal
            usuarioTransaction.guardarUsuario(usuarioForm, usuario, passwordEncoder, usuarioRepository, pacienteRepository, medicoRepository);

            redirectAttributes.addFlashAttribute("success", "Se han guardado los datos del usuario");
        } catch(Exception e){
            System.out.println("ERROR USUARIO: " + e.getMessage());
            model.addAttribute("errorUsuario", "Hubo un problema al momento de guardar los datos");
            model.addAttribute("usuarioId", usuarioForm.getId());
            model.addAttribute("roles", rolRepository.findAll());
            model.addAttribute("usuarioForm", usuarioForm);
            return "dashboard/admin/usuarios/editar";
        }

        return "redirect:/dashboard/admin/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try {
            // TODO: Crear una transacción de borrado, para intentar quitar las dependencias no importantes
            usuarioRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successDelete", "Se ha borrado el usuario exitosamente");
        } catch(Exception e){
            System.out.println("ERROR BORRAR USUARIO: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorDelete", "No se pudo borrar el usuario");
        }
        return "redirect:/dashboard/admin/usuarios";
    }

}
