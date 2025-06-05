package com.grupo2.clinicasalud.controller;

import com.grupo2.clinicasalud.model.Especialidad;
import com.grupo2.clinicasalud.model.form.ReservaCita;
import com.grupo2.clinicasalud.model.Servicio;
import com.grupo2.clinicasalud.model.form.RegistroContacto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
// import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    private final List<ReservaCita> reservas = new ArrayList<>();

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/")
    private String indice(Model model) {
        List<Servicio> servicios = Servicio.dameServicios();
        model.addAttribute("servicios", servicios);
        model.addAttribute("reserva", new ReservaCita());
        model.addAttribute("reservas", reservas);
        return "index";
    }
    @PostMapping("/guardarReserva")
    public String guardarReserva(@ModelAttribute ReservaCita reserva, Model model) {
        reservas.add(reserva);

        // Generate report
        try {
            String filePath = "src/main/resources/reports/reservas.txt";
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Revisar si el directorio existe
            FileWriter writer = new FileWriter(file, true); // Agregar al archivo de reporte
            writer.write("Nombre: " + reserva.getNombre() + "\n");

            writer.write("Email: " + reserva.getEmail() + "\n");
            writer.write("Teléfono: " + reserva.getTelefono() + "\n");
            writer.write("Servicio: " + reserva.getServicio() + "\n");
            writer.write("Fecha: " + reserva.getFecha() + "\n");
            writer.write("Hora: " + reserva.getHora() + "\n");
            writer.write("=====================================\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Servicio> servicios = Servicio.dameServicios();
        model.addAttribute("servicios", servicios);
        model.addAttribute("reserva", new ReservaCita());
        model.addAttribute("reservas", reservas);
        return "index";
    }

    @GetMapping("/especialidades")
    private String especialidades(Model model) {

        // List<Especialidad> especialidades = Especialidad.dameEspecialidades();
        List<Especialidad> especialidades = new ArrayList<>();
        model.addAttribute("especialidades", especialidades);
        return "especialidades";
    }

    @GetMapping("/servicios")
    private String servicios(Model model){
        return "servicios";
    }

    @GetMapping("/nosotros")
    private String nosotros(){
        return "nosotros";
    }

    @GetMapping("/privacidad")
    private String privacidad(){
        return "politica-privacidad";
    }

    @GetMapping("/terminos")
    private String terminos(){
        return "terminos-y-condiciones";
    }

    @GetMapping("/contactenos")
    private String contactenos(Model model){
        RegistroContacto contacto = new RegistroContacto("", "", "", "");
        model.addAttribute("contacto", contacto);
        return "contactenos";
    }

    @PostMapping("/contactenos")
    private String contactenosForm(Model model){
        RegistroContacto contacto = new RegistroContacto("", "", "", "");
        model.addAttribute("contacto", contacto);
        return "contactenos";
    }

}
