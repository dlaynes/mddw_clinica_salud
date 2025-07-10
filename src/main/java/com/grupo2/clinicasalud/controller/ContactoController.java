package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.Consulta;
import com.grupo2.clinicasalud.repository.ConsultaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class ContactoController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping("/contactenos")
    private String contactenos(Model model, @RequestParam(required = false) String success){
        if(success != null){
            model.addAttribute("successText", "Su consulta fue enviada satisfactoriamente. Muy pronto nos pondremos en contacto");
        }
        Consulta contacto = new Consulta();
        model.addAttribute("contacto", contacto);
        return "contactenos";
    }

    @PostMapping("/contactenos")
    private String contactenosForm(@Valid @ModelAttribute("contacto") Consulta contacto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("formErrors", "Hubo un error al validar los datos del formulario");
            model.addAttribute("contacto", contacto);
            return "contactenos";
        }
        contacto.setFechaCreacion(LocalDateTime.now());
        consultaRepository.save(contacto);
        redirectAttributes.addFlashAttribute("success", "Su consulta fue enviada satisfactoriamente. Muy pronto nos pondremos en contacto");
        return "redirect:/contactenos?success=true";
    }
}
