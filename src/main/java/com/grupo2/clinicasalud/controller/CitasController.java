package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.form.ReservaCitaEjemplo;
import com.grupo2.clinicasalud.service.CitaService;
import com.grupo2.clinicasalud.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/citas")
public class CitasController {
    private final CitaService citaService;
    private final PacienteService pacienteService;

    @Autowired
    public CitasController(CitaService citaService, PacienteService pacienteService){
        this.citaService = citaService;
        this.pacienteService = pacienteService;
    }

    @GetMapping("/nueva")
    public String nuevaCita(Model model){
        model.addAttribute("cita", new Cita());
        model.addAttribute("citas", citaService.dameCitas());
        model.addAttribute("especialidades", Especialidad.dameEspecialidades());
        return "citas";
    }

    @GetMapping("/editar")
    public String editarCita(@RequestParam long id, Model model){
        Cita cita = citaService.dameCitaPorId(id);
        if(cita == null){
            return "redirect:/citas/nueva";
        }
        model.addAttribute("cita", cita);
        model.addAttribute("citas", citaService.dameCitas());
        model.addAttribute("especialidades", Especialidad.dameEspecialidades());
        return "citas";
    }

    private Paciente nuevoPaciente(String nombres, String apellidos, String email, String telefono){
        Paciente paciente = new Paciente();
        paciente.setNombre(nombres);
        paciente.setApellido(apellidos);
        paciente.setEmail(email);
        paciente.setTelefono(telefono);
        return paciente;
    }

    private Consultorio dameConsultorio(){
        Consultorio c = new Consultorio();
        c.setId(1000);
        c.setDescripcion("Consultorio de prueba");
        c.setNombre("Ejemplo");
        c.setLatitud(-12.131273);
        c.setLongitud(-76.981304);
        c.setUbicacion("Surco");
        return c;
    }

    @PostMapping("/guardar")
    public String guardarCita(@ModelAttribute ReservaCitaEjemplo citaForm) {
        List<Especialidad> especialidades = Especialidad.dameEspecialidades();
        Optional<Especialidad> especialidadContainer = especialidades.stream().filter(especialidad1 -> {
            return String.valueOf( especialidad1.getId()).equals(citaForm.getEspecialidad());
        }).findFirst();
        if(especialidadContainer.isPresent()){
            Paciente paciente = pacienteService.damePacientePorEmail(citaForm.getEmail());
            if (paciente == null){
                paciente = nuevoPaciente(
                        citaForm.getNombre(),
                        citaForm.getApellidos(),
                        citaForm.getEmail(),
                        citaForm.getTelefono()
                );
            }
            // TODO: actualizar los datos del paciente?
            Cita cita = new Cita();
            cita.setEspecialidad(especialidadContainer.get());
            cita.setPaciente(paciente);
            cita.setEstadoCita(EstadoCita.registrada);
            cita.setConsultorio(dameConsultorio());

            LocalDate localDate = citaForm.getFecha();
            LocalTime time = citaForm.getHora();
            Date date = new Date(localDate.getYear(),localDate.getMonthValue()-1, localDate.getDayOfYear(), time.getHour(), time.getMinute());
            cita.setFechaHora(date);
            citaService.guardarCita(cita);
        }

        return "redirect:/citas/nueva";
    }

}
