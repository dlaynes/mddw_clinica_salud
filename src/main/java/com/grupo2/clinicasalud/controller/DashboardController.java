package com.grupo2.clinicasalud.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
@Configuration
public class DashboardController {

    @GetMapping("/")
    public String index(){
        return "dashboard/index";
    }
}
