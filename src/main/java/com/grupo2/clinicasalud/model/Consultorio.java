package com.grupo2.clinicasalud.model;

import com.grupo2.clinicasalud.validator.coordinates.CoordinatesConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "consultorios")
public class Consultorio {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="consultorio_id")
    private long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion")
    private String descripcion;

    @NotBlank(message = "La ubicación es obligatoria")
    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "imagen")
    private String imagen;

    @CoordinatesConstraint
    @Column(name = "latitud")
    private double latitud;

    @CoordinatesConstraint
    @Column(name = "longitud")
    private double longitud;

    public Consultorio(long id, String nombre, String descripcion, String ubicacion, String imagen, double latitud, double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Consultorio(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

}
