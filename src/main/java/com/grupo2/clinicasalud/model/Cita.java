package com.grupo2.clinicasalud.model;

import com.grupo2.clinicasalud.model.converter.EstadoCitaAttributeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "citas")
@SecondaryTables({
        @SecondaryTable(
                name = "pacientes",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "paciente_id")
        ),
        @SecondaryTable(
                name = "medicos",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "medico_id")
        ),
        @SecondaryTable(
                name = "especialidades",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "especialidad_id")
        ),
        @SecondaryTable(
                name = "consultorios",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "consultorio_id")
        )
})
public class Cita {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="cita_id")
    private String id;

    @ManyToOne()
    @JoinColumn(name="paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne()
    @JoinColumn(name="medico_id", nullable = false)
    Medico medico;

    @ManyToOne()
    @JoinColumn(name="especialidad_id", nullable = false)
    Especialidad especialidad;

    @ManyToOne()
    @JoinColumn(name="consultorio_id", nullable = false)
    Consultorio consultorio;

    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    @Convert(converter = EstadoCitaAttributeConverter.class)
    EstadoCita estadoCita;

    @NotBlank(message = "El motivo es obligatorio")
    @Column(name = "motivo")
    private String motivo;

    public Cita(String id, Paciente paciente, Medico medico, Especialidad especialidad, Consultorio consultorio, Date fechaHora, EstadoCita estadoCita, String motivo) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.especialidad = especialidad;
        this.consultorio = consultorio;
        this.fechaHora = fechaHora;
        this.estadoCita = estadoCita;
        this.motivo = motivo;
    }

    public Cita(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
