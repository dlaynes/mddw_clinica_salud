package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.form.ReservaCitaForm;
import com.grupo2.clinicasalud.repository.*;
import com.grupo2.clinicasalud.security.utils.PasswordGen;
import com.grupo2.clinicasalud.service.transactions.ReservarCitaTransaction;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

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


    @GetMapping("/")
    private String indice(Model model) {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        List<Servicio> servicios = servicioRepository.findAll();
        model.addAttribute("especialidades", especialidades);
        model.addAttribute("servicios", servicios);
        model.addAttribute("reserva", new ReservaCitaForm());

        return "index";
    }

    @PostMapping("/")
    public String guardarReserva(@Valid @ModelAttribute("reserva") ReservaCitaForm reserva, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("citaError", "Debe llenar correctamente los datos");
            List<Especialidad> especialidades = especialidadRepository.findAll();
            List<Servicio> servicios = servicioRepository.findAll();
            model.addAttribute("especialidades", especialidades);
            model.addAttribute("servicios", servicios);
            model.addAttribute("reserva", reserva);
            return "index";
        }
        Optional<Especialidad> especialidadContainer = especialidadRepository.findById(reserva.getEspecialidad_id());
        if(especialidadContainer.isEmpty()){
            redirectAttributes.addFlashAttribute("citaError", "Se debe seleccionar una especialidad válida");
            List<Especialidad> especialidades = especialidadRepository.findAll();
            List<Servicio> servicios = servicioRepository.findAll();
            model.addAttribute("especialidades", especialidades);
            model.addAttribute("servicios", servicios);
            model.addAttribute("reserva", reserva);
            return "index";
        }
        Optional<Usuario> usuarioContainer = usuarioRepository.findByEmail(reserva.getEmail());

        if(usuarioContainer.isEmpty()){
            String password = PasswordGen.generatePasPassword(16);
            reservaCitaTransacion.guardarCita(
                    reserva, especialidadContainer.get(),
                    citaRepository, pacienteRepository, usuarioRepository, roleRepository, consultorioRepository,
                    password, passwordEncoder);

            redirectAttributes.addFlashAttribute("registroEmail", reserva.getEmail());
            redirectAttributes.addFlashAttribute("registroPassword", password);
            return "redirect:/cita-creada";

        } else {
            redirectAttributes.addFlashAttribute("citaError", "Ya existe un paciente con el correo indicado. Por favor utilice un correo nuevo o ingrese a su cuenta");
            List<Especialidad> especialidades = especialidadRepository.findAll();
            List<Servicio> servicios = servicioRepository.findAll();
            model.addAttribute("especialidades", especialidades);
            model.addAttribute("servicios", servicios);
            model.addAttribute("reserva", reserva);
            return "index";
        }
    }

    @GetMapping("/cita-creada")
    public String reservaCreada(HttpServletRequest request, Model model){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        do {
            if(inputFlashMap == null) break;
            String registroEmail = (String) inputFlashMap.get("registroEmail");
            String registroPassword = (String) inputFlashMap.get("registroPassword");
            if(registroEmail == null || registroPassword == null) break;

            model.addAttribute("registroEmail", registroEmail);
            model.addAttribute("registroPassword", registroPassword);
            return "cita_creada";
        } while (false);
        return "redirect:/";
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
}
