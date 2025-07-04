package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.form.ReservaCita;
import com.grupo2.clinicasalud.repository.*;
import com.grupo2.clinicasalud.service.transactions.ReservarCitaTransaction;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private ConsultorioRepository consultorioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReservarCitaTransaction reservaCitaTransacion;

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/")
    private String indice(Model model, @RequestParam(required = false) String success) {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        model.addAttribute("especialidades", especialidades);
        model.addAttribute("reserva", new ReservaCita());
        if(success != null){
            // TO DO: enviar correo
            model.addAttribute("successText", "Se ha registrado su solicitud, y lo estaremos contactando pronto");
        }
        return "index";
    }

    @PostMapping("/guardarReserva")
    public String guardarReserva(@ModelAttribute ReservaCita reserva, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            return "redirect:/";
        }

        Optional<Especialidad> especialidadContainer = especialidadRepository.findById(reserva.getEspecialidad_id());
        if(especialidadContainer.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Se debe seleccionar una especialidad válida");
            return "redirect:/";
        }
        Optional<Usuario> usuarioContainer = usuarioRepository.findByEmail(reserva.getEmail());

        if(usuarioContainer.isEmpty()){
            reservaCitaTransacion.guardarCita(
                    reserva, especialidadContainer.get(),
                    citaRepository, pacienteRepository, usuarioRepository, roleRepository, consultorioRepository, passwordEncoder);

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
    private String contactenos(Model model){
        Consulta contacto = new Consulta();
        model.addAttribute("contacto", contacto);
        return "contactenos";
    }

    @PostMapping("/contactenos")
    private String contactenosForm(@ModelAttribute Consulta consulta, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "redirect:/contactenos";
        }
        consulta.setFechaCreacion(new Date());
        consultaRepository.save(consulta);
        redirectAttributes.addFlashAttribute("success", "Su consulta fue enviada satisfactoriamente. Muy pronto nos pondremos en contacto");
        return "redirect:/contactenos?success=true";
    }

}
