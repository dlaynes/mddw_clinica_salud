package com.grupo2.clinicasalud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "especialidades")
public class Especialidad {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="especialidad_id")
    private long id;

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

    @Column(name = "color")
    private String color;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Especialidad(long id, String nombre, String descripcion, Date fechaCreacion, String color){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.color = color;
        this.fechaCreacion = fechaCreacion;
    }

    public static List<Especialidad> dameEspecialidades(){
        return Arrays.asList(
                new Especialidad(1,"Medicina General", "Atención primaria integral", new Date(), "primary"),
                new Especialidad(2,"Cirugía", "Procedimientos quirúrgicos",new Date(),  "danger"),
                new Especialidad(3,"Pediatría", "Atención infantil", new Date(),  "success"),
                new Especialidad(4,"Ginecología", "Salud femenina", new Date(),  "warning"),
                new Especialidad(5,"Cardiología", "Sistema cardiovascular",  new Date(), "info"),
                new Especialidad(6,"Neurología", "Sistema nervioso", new Date(), "secondary"),
                new Especialidad(7,"Odontología", "Salud bucal", new Date(), "dark"),
                new Especialidad(8,"Psiquiatría", "Salud mental", new Date(), "success")

        );
    }
}
