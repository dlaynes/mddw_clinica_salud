package com.grupo2.clinicasalud.controller.dashboardMedico;

import com.grupo2.clinicasalud.model.HistorialMedico;
import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.Receta;
import com.grupo2.clinicasalud.repository.HistorialMedicoRepository;
import com.grupo2.clinicasalud.repository.RecetaRepository;
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
@RequestMapping("/dashboard/doctor/historial")
public class HistorialDoctorController {

    @Autowired
    RecetaRepository recetaRepository;

    @Autowired
    HistorialMedicoRepository historialMedicoRepository;

    @Autowired
    DetalleUsuarioService detalleUsuarioService;

    @GetMapping
    public String index(Model model){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("ERROR MEDICO no encontrado");
            return "redirect:/dashboard/index";
        }

        List<HistorialMedico> historialMedicoList = historialMedicoRepository.findByMedicoIdOrderByFechaConsultaDesc(medico.getId());
        model.addAttribute("historialList", historialMedicoList);

        return "dashboard/doctor/historial/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("ERROR MEDICO no encontrado");
            return "redirect:/dashboard/index";
        }

        Optional<HistorialMedico> historialMedicoOptional = historialMedicoRepository.findOneByIdAndMedicoId(id, medico.getId());
        if(historialMedicoOptional.isEmpty()){
            return "redirect:/dashboard/index";
        }
        model.addAttribute("historial", historialMedicoOptional.get());
        model.addAttribute("recetasList", recetaRepository.findByHistorialMedicoId(id));
        return "dashboard/doctor/historial/ver";
    }

    @GetMapping("/ver/{id}/receta")
    public String receta(@PathVariable(name = "id") Long historialMedicoId, Model model){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("ERROR MEDICO no encontrado");
            return "redirect:/dashboard/index";
        }

        Optional<HistorialMedico> historialMedicoOptional = historialMedicoRepository.findOneByIdAndMedicoId(historialMedicoId, medico.getId());
        if(historialMedicoOptional.isEmpty()){
            return "redirect:/dashboard/index";
        }
        Receta receta = new Receta();

        model.addAttribute("receta", receta);
        model.addAttribute("historial", historialMedicoOptional.get());
        return "dashboard/doctor/historial/receta";
    }


    @GetMapping("/ver/{id}/receta/{recetaId}")
    public String receta(@PathVariable(name = "id") Long historialMedicoId, @PathVariable(name = "recetaId") Long recetaId, Model model){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("ERROR MEDICO no encontrado");
            return "redirect:/dashboard/index";
        }

        Optional<HistorialMedico> historialMedicoOptional = historialMedicoRepository.findOneByIdAndMedicoId(historialMedicoId, medico.getId());
        if(historialMedicoOptional.isEmpty()){
            return "redirect:/dashboard/index";
        }
        Receta receta;
        Optional<Receta> recetaOptional = recetaRepository.findOneByIdAndHistorialMedicoId(recetaId, historialMedicoId);
        if(recetaOptional.isEmpty()){
            return "redirect:/dashboard/index";
        }
        receta = recetaOptional.get();

        model.addAttribute("receta", receta);
        model.addAttribute("historial", historialMedicoOptional.get());
        return "dashboard/doctor/historial/receta";
    }

    @PostMapping("/ver/{id}/receta")
    public String guardarReceta(
            @PathVariable(name = "id") Long historialMedicoId,
            @Valid @ModelAttribute("receta") Receta receta,
            BindingResult result,
            RedirectAttributes attributes,
            Model model){
        Medico medico = detalleUsuarioService.getMedicoActual();
        if(medico == null){
            System.out.println("ERROR MEDICO no encontrado");
            return "redirect:/dashboard/index";
        }

        Optional<HistorialMedico> historialMedicoOptional = historialMedicoRepository.findOneByIdAndMedicoId(historialMedicoId, medico.getId());
        if(historialMedicoOptional.isEmpty()){
            return "redirect:/dashboard/index";
        }

        if(result.hasErrors()){
            model.addAttribute("receta", receta);
            model.addAttribute("historial", historialMedicoOptional.get());
            return "dashboard/doctor/historial/receta";
        }

        receta.setHistorialMedico(historialMedicoOptional.get());
        recetaRepository.save(receta);
        attributes.addFlashAttribute("recetaSuccess", "Se guardaron los datos de la receta");

        return "redirect:/dashboard/doctor/historial/ver/"+historialMedicoId;
    }

    @PostMapping("/ver/{id}/receta/{recetaId}/borrar")
    public String borrarReceta(
            @PathVariable(name = "id") Long historialMedicoId,
            @PathVariable(name = "recetaId") Long recetaId,
            @Valid @ModelAttribute("receta") Receta receta,
            RedirectAttributes attributes) {
        Medico medico = detalleUsuarioService.getMedicoActual();
        if (medico == null) {
            System.out.println("ERROR MEDICO no encontrado");
            return "redirect:/dashboard/index";
        }

        Optional<HistorialMedico> historialMedicoOptional = historialMedicoRepository.findOneByIdAndMedicoId(historialMedicoId, medico.getId());
        if (historialMedicoOptional.isEmpty()) {
            return "redirect:/dashboard/index";
        }

        Optional<Receta> recetaOptional = recetaRepository.findOneByIdAndHistorialMedicoId(recetaId, historialMedicoId);
        if(recetaOptional.isEmpty()){
            return "redirect:/dashboard/index";
        }
        try {
            recetaRepository.delete(recetaOptional.get());
        } catch(RuntimeException e){
            attributes.addFlashAttribute("recetaDeletedError", "No se pudo borrar la receta");
            return "redirect:/dashboard/doctor/historial/ver/"+historialMedicoId;
        }

        attributes.addFlashAttribute("recetaDeleted", "Se borraron los datos de la receta");
        return "redirect:/dashboard/doctor/historial/ver/"+historialMedicoId;
    }

}
