package com.grupo2.clinicasalud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="especialidad_id")
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public Servicio(String id, String nombre, String descripcion, Date fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public static List<Servicio> dameServicios(){
        return Arrays.asList(
                new Servicio("consulta-medica","Consulta Médica", "Atención personalizada para diagnosticar y tratar tus necesidades de salud", new Date()),
                new Servicio("laboratorio-clinico","Laboratorio Clínico", "Análisis clínicos precisos para un diagnóstico confiable", new Date()),
                new Servicio("radiologia","Radiología", "Tecnología avanzada para estudios de imagen y diagnóstico", new Date())
        );
    }
}
