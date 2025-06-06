package com.grupo2.clinicasalud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class Cita {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="cita_id")
    private String id;

    Paciente paciente;

    Medico medico;

    Especialidad especialidad;

    Consultorio consultorio;

    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    EstadoCita estadoCita;

    @NotBlank(message = "El motivo es obligatorio")
    @Column(name = "motivo")
    private String motivo;

}
