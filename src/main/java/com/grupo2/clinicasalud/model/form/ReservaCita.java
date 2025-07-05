package com.grupo2.clinicasalud.model.form;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaCita {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 150, message = "El apellido debe tener entre 2 y 150 caracteres")
    @Column(name = "apellidos")
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Size(min = 5, max = 150, message = "El email debe tener entre 2 y 150 caracteres")
    @Email
    @Column(name = "email", length = 150)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 5, max = 25, message = "El teléfono debe tener entre 5 y 25 caracteres")
    @Column(name = "telefono", length = 25)
    private String telefono;

    @NotNull(message = "Se debe seleccionar una fecha aproximada")
    @Pattern(
            regexp = "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(20)\\d{2}",
            message = "Se debe indicar una fecha válida"
    )
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private String fecha;

    @NotNull(message = "Se debe seleccionar una hora aproximada")
    @Pattern(
            regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Se debe indicar una hora válida"
    )
    private String hora;

    @NotNull(message = "Se debe seleccionar una especialidad")
    private Long especialidad_id;

    @Column(name = "motivo")
    @Size(min=2, max=2000)
    private String motivo;

    // Getters and Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Long getEspecialidad_id() {
        return especialidad_id;
    }

    public void setEspecialidad_id(Long especialidad_id) {
        this.especialidad_id = especialidad_id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
