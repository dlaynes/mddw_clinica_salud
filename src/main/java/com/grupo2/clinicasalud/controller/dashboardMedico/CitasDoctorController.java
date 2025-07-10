package com.grupo2.clinicasalud.controller.dashboardMedico;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.repository.CitaRepository;
import com.grupo2.clinicasalud.repository.HistorialMedicoRepository;
import com.grupo2.clinicasalud.repository.ServicioRepository;
import com.grupo2.clinicasalud.service.auth.DetalleUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/doctor/citas")
public class CitasDoctorController {

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    HistorialMedicoRepository historialMedicoRepository;

    @Autowired
    DetalleUsuarioService detalleUsuarioService;

    @GetMapping
    public String index(Model model){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("USUARIO MEDICO NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        List<Cita> citaList = citaRepository.findByMedicoIdOrderByFechaHoraDesc(medico.getId());
        model.addAttribute("citas", citaList);

        return "dashboard/doctor/citas/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("USUARIO PACIENTE NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        Optional<Cita> citaOpt = citaRepository.findOneByIdAndMedicoId(id, medico.getId());
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/doctor/citas";
        }
        Cita cita =  citaOpt.get();
        model.addAttribute("cita", cita);
        return "dashboard/doctor/citas/ver";
    }

    // Se habilita una cita cuando el paciente ha llegado al consultorio y está haciendo cola
    @GetMapping("/enEspera/{id}")
    public String enEspera(@PathVariable Long id, RedirectAttributes attributes){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("USUARIO MEDICO NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        Optional<Cita> citaOpt = citaRepository.findOneByIdAndMedicoIdAndEstadoCita(id, medico.getId(), EstadoCita.programada);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/doctor/citas";
        }
        Cita cita = citaOpt.get();
        cita.setEstadoCita(EstadoCita.enEspera);
        citaRepository.save(cita);

        attributes.addFlashAttribute("enEsperaSuccess", "Se habilitó la cita, para que el cliente pueda ser atendido");
        return "redirect:/dashboard/doctor/citas";
    }

    @GetMapping("/resultadoCita/{id}")
    public String llenarResultados(@PathVariable Long id, Model model){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("USUARIO MEDICO NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }

        Optional<Cita> citaOpt = citaRepository.findOneByIdAndMedicoIdAndEstadoCita(id, medico.getId(), EstadoCita.enEspera);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/doctor/citas";
        }

        model.addAttribute("cita", citaOpt.get());
        model.addAttribute("servicios", servicioRepository.findAll());
        model.addAttribute("historial", new HistorialMedico());
        return "dashboard/doctor/citas/resultado-cita";
    }

    @PostMapping("/resultadoCita/{id}")
    public String formResultados(@PathVariable Long id,
                                 @Valid @ModelAttribute("historial") HistorialMedico historialMedico,
                                 BindingResult result,
                                 RedirectAttributes attributes,
                                 Model model){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("USUARIO MEDICO NO ENCONTRADO");
            return "redirect:/dashboard/index";
        }
        Optional<Cita> citaOpt = citaRepository.findOneByIdAndMedicoIdAndEstadoCita(id, medico.getId(), EstadoCita.enEspera);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/doctor/citas";
        }
        if(result.hasErrors()){
            model.addAttribute("cita", citaOpt.get());
            model.addAttribute("servicios", servicioRepository.findAll());
            model.addAttribute("historial", historialMedico);
            return "dashboard/doctor/citas/resultado-cita";
        }
        Cita cita = citaOpt.get();
        historialMedico.setEspecialidad(cita.getEspecialidad());
        historialMedico.setMedico(medico);
        historialMedico.setPaciente(cita.getPaciente());
        historialMedico.setCita(cita);
        historialMedicoRepository.save(historialMedico);
        cita.setHistorialMedico(historialMedico);
        cita.setEstadoCita(EstadoCita.completada);
        citaRepository.save(cita);
        attributes.addFlashAttribute("crearHistorial", "Fue creado el historial médico con éxito");
        return "redirect:/dashboard/doctor/historial/ver/" + historialMedico.getId();
    }

    @GetMapping("/agenda")
    public String agenda(){
        return "dashboard/doctor/citas/agenda";
    }

}
