package com.grupo2.clinicasalud.controller.dashboardCliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/cliente/agenda")
public class AgendaClienteController {
    @GetMapping("")
    public String agenda(){
        return "dashboard/cliente/agenda/index";
    }
}
