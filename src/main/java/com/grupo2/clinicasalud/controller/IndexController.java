package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.converter.GeneroAttributeConverter;
import com.grupo2.clinicasalud.model.form.ReservaCita;
import com.grupo2.clinicasalud.model.form.RegistroContacto;
import com.grupo2.clinicasalud.repository.*;
import com.grupo2.clinicasalud.security.utils.PasswordGen;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    private RolRepository roleRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/")
    private String indice(Model model) {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        model.addAttribute("especialidades", especialidades);
        model.addAttribute("reserva", new ReservaCita());
        return "index";
    }

    @PostMapping("/guardarReserva")
    public String guardarReserva(@Valid @ModelAttribute ReservaCita reserva, RedirectAttributes redirectAttributes) {
        Optional<Especialidad> especialidadContainer = especialidadRepository.findById(reserva.getEspecialidad_id());
        if(especialidadContainer.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Se debe seleccionar una especialidad válida");
            return "redirect:/";
        }
        Optional<Usuario> usuarioContainer = usuarioRepository.findByEmail(reserva.getEmail());
        Paciente paciente;
        Usuario usuario;
        Cita cita;
        if(usuarioContainer.isEmpty()){
            usuario = new Usuario();
            usuario.setEmail(reserva.getEmail());
            String password = PasswordGen.generatePasPassword(16);
            usuario.setPassword(passwordEncoder.encode(password));
            Set<Rol> roles = new HashSet<>();
            Rol clienteRol = roleRepository.findByNombre("Cliente")
                    .orElseThrow(() -> new RuntimeException("Error: Rol Cliente no encontrado."));
            roles.add(clienteRol);
            usuario.setRoles(roles);
            usuarioRepository.save(usuario);

            // TO DO: enviar un correo con la información. Luego pedirle al usuario que cambie su contraseña
            System.out.println("Se creo un nuevo usuario mediante el formulario de citas rápidas. E-mail: " + reserva.getEmail() + ", password: " + password);

            paciente = new Paciente();
            paciente.setNombre(reserva.getNombre());
            paciente.setApellido(reserva.getApellidos());
            paciente.setEmail(reserva.getEmail());
            paciente.setTelefono(reserva.getTelefono());
            paciente.setUsuario(usuario);
            pacienteRepository.save(paciente);

            cita = new Cita();
            cita.setEstadoCita(EstadoCita.registrada);
            cita.setEspecialidad(especialidadContainer.get());
            cita.setMotivo(reserva.getMotivo());
            LocalDate localDate = reserva.getFecha();
            LocalTime localTime = reserva.getHora();
            Instant instant = localTime.atDate(LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfYear()))
                    .atZone(ZoneId.systemDefault()).toInstant();
            cita.setFechaHora(Date.from(instant));
            cita.setPaciente(paciente);
            citaRepository.save(cita);

            redirectAttributes.addFlashAttribute("success", "Se ha registrado su cita satisfactoriamente");
            return "redirect:/?success=true";

        } else {
            redirectAttributes.addFlashAttribute("error", "Ya existe un paciente con el correo indicado. Por favor utilice uno nuevo");
            return "redirect:/";
        }
    }

    @GetMapping("/especialidades")
    private String especialidades(Model model) {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        model.addAttribute("especialidades", especialidades);
        return "especialidades";
    }

    @GetMapping("/servicios")
    private String servicios(Model model){
        List<Servicio> servicios = servicioRepository.findAll();
        model.addAttribute("servicios", servicios);
        return "servicios";
    }

    @GetMapping("/nosotros")
    private String nosotros(){
        return "nosotros";
    }

    @GetMapping("/privacidad")
    private String privacidad(){
        return "politica-privacidad";
    }

    @GetMapping("/terminos")
    private String terminos(){
        return "terminos-y-condiciones";
    }

    @GetMapping("/contactenos")
    private String contactenos(Model model, @RequestParam(required = false) String success){
        RegistroContacto contacto = new RegistroContacto("", "", "", "");
        if(success != null){
            model.addAttribute("success", success);
        }
        model.addAttribute("contacto", contacto);
        return "contactenos";
    }

    @PostMapping("/contactenos")
    private String contactenosForm(@Valid @ModelAttribute Consulta consulta, RedirectAttributes redirectAttributes){
        consultaRepository.save(consulta);
        redirectAttributes.addFlashAttribute("success", "Su consulta fue enviada satisfactoriamente. Muy pronto nos pondremos en contacto");
        return "redirect:/contactenos?success=true";
    }

}
