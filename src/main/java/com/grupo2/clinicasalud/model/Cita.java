package com.grupo2.clinicasalud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo2.clinicasalud.model.converter.EstadoCitaAttributeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "citas")
public class Cita {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="cita_id", columnDefinition = "BIGINT")
    private long id;

    @ManyToOne()
    @JoinColumn(name="paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne()
    @JoinColumn(name="medico_id")
    Medico medico;

    @ManyToOne()
    @JoinColumn(name="especialidad_id", nullable = false)
    Especialidad especialidad;

    // En un proyecto más avanzado, se consideraría el consultorio a la hora de tomar decisiones
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="consultorio_id", nullable = false)
    Consultorio consultorio;

    // Para facilitar la programación, se considera que la fecha y hora elegida por el administrador no variará
    // En la práctica debería haber 2 campos de fecha extra: fechaDeseada y fechaDeAtencion + duración
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date fechaHora;

    @Convert(converter = EstadoCitaAttributeConverter.class)
    @Column(name="estado", length = 16)
    EstadoCita estadoCita;

    @NotBlank(message = "El motivo es obligatorio")
    @Column(name = "motivo", columnDefinition="TEXT")
    @Size(max=2000)
    private String motivo;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name="historial_id")
    private HistorialMedico historialMedico;

    @Column(name="fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }
}
