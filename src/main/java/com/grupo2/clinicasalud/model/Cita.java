package com.grupo2.clinicasalud.model;

import com.grupo2.clinicasalud.model.converter.EstadoCitaAttributeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "citas")
public class Cita {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="cita_id", columnDefinition = "BIGINT")
    private long id;

    @ManyToOne(targetEntity = Paciente.class)
    @JoinColumn(name="paciente_id", nullable = false, referencedColumnName = "paciente_id")
    private Paciente paciente;

    @ManyToOne(targetEntity = Medico.class)
    @JoinColumn(name="medico_id", referencedColumnName = "medico_id")
    Medico medico;

    @ManyToOne(targetEntity = Especialidad.class)
    @JoinColumn(name="especialidad_id", nullable = false, referencedColumnName = "especialidad_id")
    Especialidad especialidad;

    @ManyToOne(targetEntity = Consultorio.class)
    @JoinColumn(name="consultorio_id", nullable = false, referencedColumnName = "consultorio_id")
    Consultorio consultorio;

    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    @Convert(converter = EstadoCitaAttributeConverter.class)
    @Column(name="estado", length = 16)
    EstadoCita estadoCita;

    @NotBlank(message = "El motivo es obligatorio")
    @Column(name = "motivo", columnDefinition="TEXT")
    @Size(max=2000)
    private String motivo;

    public Cita(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
