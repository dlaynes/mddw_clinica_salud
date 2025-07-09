package com.grupo2.clinicasalud.controller.dashboardAdmin;

import com.grupo2.clinicasalud.model.Cita;
import com.grupo2.clinicasalud.model.Consulta;
import com.grupo2.clinicasalud.model.EstadoCita;
import com.grupo2.clinicasalud.repository.CitaRepository;
import com.grupo2.clinicasalud.repository.MedicoRepository;
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
@RequestMapping("/dashboard/admin/citas")
public class CitasController {

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @GetMapping
    public String index(Model model){
        List<Cita> citaList = citaRepository.findAll();
        model.addAttribute("citas", citaList);

        return "dashboard/admin/citas/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model){
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/admin/citas";
        }
        model.addAttribute("cita", citaOpt.get());
        return "dashboard/admin/citas/ver";
    }

    @GetMapping("/asignar/{id}")
    public String asignar(@PathVariable Long id, Model model){
        Optional<Cita> citaOpt = citaRepository.findOneByIdAndEstadoCita(id, EstadoCita.registrada);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/admin/citas";
        }
        Cita cita = citaOpt.get();
        model.addAttribute("medicos", medicoRepository.dameMedicosPorEspecialidad(cita.getEspecialidad().getId()));
        model.addAttribute("cita", cita);
        return "dashboard/admin/citas/asignar";
    }

    @PostMapping("/asignar")
    public String asignar(@Valid @ModelAttribute("cita") Cita cita, BindingResult result, Model model, RedirectAttributes attributes) {
        if(result.hasErrors()){
            model.addAttribute("medicos", medicoRepository.dameMedicosPorEspecialidad(cita.getEspecialidad().getId()));
            model.addAttribute("cita", cita);
            return "dashboard/admin/citas/asignar";
        }
        Optional<Cita> citaRealOpt = citaRepository.findById(cita.getId());
        if(citaRealOpt.isEmpty()){
            return "redirect:/dashboard/admin/citas";
        }
        Cita citaReal = citaRealOpt.get();
        citaReal.setFechaHora(cita.getFechaHora());
        citaReal.setMedico(cita.getMedico());
        citaReal.setEstadoCita(EstadoCita.programada);
        citaRepository.save(citaReal);

        attributes.addFlashAttribute("asignarSuccess", "Se habilitó la cita");
        return "redirect:/dashboard/admin/citas";
    }

    // Se habilita una cita cuando el paciente ha llegado al consultorio
    @GetMapping("/enEspera/{id}")
    public String enEspera(@PathVariable Long id, RedirectAttributes attributes){
        Optional<Cita> citaOpt = citaRepository.findOneByIdAndEstadoCita(id, EstadoCita.programada);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/admin/citas";
        }
        Cita cita = citaOpt.get();
        cita.setEstadoCita(EstadoCita.enEspera);
        citaRepository.save(cita);

        attributes.addFlashAttribute("enEsperaSuccess", "Se habilitó la cita");
        return "redirect:/dashboard/admin/citas";
    }

    @GetMapping("/rechazar/{id}")
    public String rechazar(@PathVariable Long id, RedirectAttributes attributes){
        Optional<Cita> citaOpt = citaRepository.findOneByIdAndEstadoCita(id, EstadoCita.registrada);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/admin/citas";
        }
        Cita cita = citaOpt.get();
        cita.setEstadoCita(EstadoCita.rechazada);
        citaRepository.save(cita);

        attributes.addFlashAttribute("rechazarSuccess", "Se rechazó la cita");
        return "redirect:/dashboard/admin/citas";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, RedirectAttributes attributes){
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if(citaOpt.isEmpty()){
            return "redirect:/dashboard/admin/citas";
        }

        Cita cita = citaOpt.get();
        EstadoCita estadoCita = cita.getEstadoCita();
        if(!estadoCita.equals(EstadoCita.programada) && !estadoCita.equals(EstadoCita.enEspera)){
            return "redirect:/dashboard/admin/citas";
        }

        cita.setEstadoCita(EstadoCita.cancelada);
        citaRepository.save(cita);

        attributes.addFlashAttribute("cancelarSuccess", "Se canceló la cita");
        return "redirect:/dashboard/admin/citas";
    }

}
