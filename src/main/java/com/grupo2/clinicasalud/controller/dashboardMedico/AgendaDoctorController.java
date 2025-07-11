package com.grupo2.clinicasalud.controller.dashboardMedico;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/doctor/agenda")
public class AgendaDoctorController {

    @GetMapping()
    public String agenda(){
        return "dashboard/doctor/agenda/index";
    }
}
