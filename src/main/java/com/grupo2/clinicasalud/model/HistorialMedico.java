package com.grupo2.clinicasalud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "historial_medico")
public class HistorialMedico {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="historial_id", columnDefinition = "BIGINT")
    private long id;

    @ManyToOne()
    @JoinColumn(name="paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne()
    @JoinColumn(name="medico_id", nullable = false)
    private Medico medico;

    @ManyToOne()
    @JoinColumn(name="especialidad_id", nullable = false)
    private Especialidad especialidad;

    @ManyToOne()
    @JoinColumn(name="servicio_id")
    private Servicio servicio;

    @Column(name = "fecha_consulta")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConsulta;

    @Size(min = 5, max = 2000, message = "El diagnóstico debe tener entre 5 y 2000 caracteres")
    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Size(min = 5, max = 2000, message = "El tratamiento debe tener entre 5 y 2000 caracteres")
    @Column(name = "tratamiento", columnDefinition = "TEXT")
    private String tratamiento;

    @Size(min = 5, max = 2000, message = "Las notas deben tener entre 5 y 5000 caracteres")
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;

    @OneToMany(mappedBy = "historialMedico", fetch = FetchType.LAZY)
    private List<Receta> recetas;

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

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
