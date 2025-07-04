package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.converter.GeneroAttributeConverter;
import com.grupo2.clinicasalud.model.form.ReservaCitaEjemplo;
import com.grupo2.clinicasalud.service.CitaService;
import com.grupo2.clinicasalud.service.ConsultorioService;
import com.grupo2.clinicasalud.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/citas")
public class CitasController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ConsultorioService consultorioService;

    @GetMapping("/nueva")
    public String nuevaCita(Model model){
        model.addAttribute("cita", new ReservaCitaEjemplo());
        model.addAttribute("citas", citaService.dameCitas());
        model.addAttribute("generos", Genero.values());
        model.addAttribute("especialidades", Especialidad.dameEspecialidades());
        return "citas";
    }

    @GetMapping("/editar")
    public String editarCita(@RequestParam long id, Model model){
        Cita cita = citaService.dameCitaPorId(id);
        if(cita == null){
            return "redirect:/citas/nueva";
        }
        Paciente paciente = cita.getPaciente();
        if(paciente == null){
            return "redirect:/citas/nueva";
        }
        ReservaCitaEjemplo form = new ReservaCitaEjemplo();
        form.setId(cita.getId());
        form.setApellidos(paciente.getApellido());
        form.setNombre(paciente.getNombre());
        form.setEmail(paciente.getEmail());
        form.setTelefono(paciente.getTelefono());
        form.setGenero(paciente.getGenero().toString());
        form.setEdicion(true);

        model.addAttribute("cita", form);
        model.addAttribute("citas", citaService.dameCitas());
        model.addAttribute("especialidades", Especialidad.dameEspecialidades());
        return "citas";
    }

    private Paciente nuevoPaciente(String nombres, String apellidos, String email, String telefono, Genero genero){
        Paciente paciente = new Paciente();
        paciente.setNombre(nombres);
        paciente.setApellido(apellidos);
        paciente.setEmail(email);
        paciente.setTelefono(telefono);
        paciente.setGenero(genero);
        return paciente;
    }

    private Consultorio dameConsultorio(){
        Consultorio c = new Consultorio();
        c.setId(1000);
        c.setDescripcion("Consultorio de prueba");
        c.setNombre("Ejemplo");
        c.setLatitud(BigDecimal.valueOf(-12.131273));
        c.setLongitud(BigDecimal.valueOf(-76.981304));
        c.setUbicacion("Surco");
        return c;
    }

    @PostMapping("/guardar")
    public String guardarCita(@ModelAttribute ReservaCitaEjemplo citaForm) {
        List<Especialidad> especialidades = Especialidad.dameEspecialidades();
        Optional<Especialidad> especialidadContainer = especialidades.stream().filter(especialidad1 -> {
            return String.valueOf( especialidad1.getId()).equals(citaForm.getEspecialidad());
        }).findFirst();
        try {
            if(especialidadContainer.isPresent()){
                Paciente paciente = pacienteService.damePacientePorEmail(citaForm.getEmail());
                if (paciente == null){
                    GeneroAttributeConverter c = new GeneroAttributeConverter();
                    paciente = nuevoPaciente(
                            citaForm.getNombre(),
                            citaForm.getApellidos(),
                            citaForm.getEmail(),
                            citaForm.getTelefono(),
                            c.convertToEntityAttribute(citaForm.getGenero())
                    );
                }
                Cita cita;
                long id = citaForm.getId();
                if(id != 0){
                    cita = citaService.dameCitaPorId(id);
                    if(cita == null){
                        System.out.println("Error al buscar cita: "+id);
                        cita = new Cita();
                    }
                } else {
                    cita = new Cita();
                }

                // TODO: actualizar los datos del paciente?
                cita.setEspecialidad(especialidadContainer.get());
                cita.setPaciente(paciente);
                cita.setEstadoCita(EstadoCita.registrada);
                Consultorio consultorio = dameConsultorio();
                if(!consultorioService.existeConsultorioPorId(consultorio.getId())){
                    consultorioService.guardarConsultorio(consultorio);
                }
                cita.setConsultorio(consultorio);
                LocalDate localDate = citaForm.getFecha();
                LocalTime time = citaForm.getHora();
                Date date = new Date(localDate.getYear(),localDate.getMonthValue()-1, localDate.getDayOfYear(), time.getHour(), time.getMinute());
                cita.setMotivo(citaForm.getMotivo());
                citaService.guardarCita(cita);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return "redirect:/citas/nueva";
    }

}
