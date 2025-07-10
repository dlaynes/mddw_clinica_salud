package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.service.auth.DetalleUsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ControllerUtils {

    @Autowired
    DetalleUsuarioService detalleUsuarioService;

    @ModelAttribute("requestURI")
    String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("nombreUsuario")
    String getNombreUsuario( @AuthenticationPrincipal Usuario user){
        if(user == null){
            return "Visitante";
        }
        if(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Admin"))){
            return "Admin";
        }
        if(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Doctor"))){
            Medico medico = detalleUsuarioService.getMedicoActual();
            if(medico == null){
                return "Nombre Doctor";
            }
            return medico.getApellido() + ", " + medico.getNombre();
        }
        if(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Cliente"))){
            Paciente paciente = detalleUsuarioService.getPacienteActual();
            if(paciente == null){
                return "Nombre Paciente";
            }
            return paciente.getApellido() + ", " + paciente.getNombre();
        }
        return "Desconocido";
    }
}