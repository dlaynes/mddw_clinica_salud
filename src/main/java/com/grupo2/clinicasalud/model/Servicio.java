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
    @Column(name ="servicio_id", columnDefinition = "BIGINT")
    private long id;

    @NotBlank(message = "La etiqueta es obligatorio")
    @Size(min = 2, max = 100, message = "La etiqueta debe tener entre 2 y 100 caracteres")
    @Column(name = "slug", length = 100)
    private String slug;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    @Column(name = "nombre", length = 150)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 2000, message = "La descripción no debe tener más de 2000 caracteres")
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public Servicio(long id, String slug, String nombre, String descripcion, Date fechaCreacion) {
        this.id = id;
        this.slug = slug;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
    }

    public Servicio(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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
                new Servicio(1, "consulta-medica","Consulta Médica", "Atención personalizada para diagnosticar y tratar tus necesidades de salud", new Date()),
                new Servicio(2, "laboratorio-clinico","Laboratorio Clínico", "Análisis clínicos precisos para un diagnóstico confiable", new Date()),
                new Servicio(3, "radiologia","Radiología", "Tecnología avanzada para estudios de imagen y diagnóstico", new Date())
        );
    }
}
