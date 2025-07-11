package com.grupo2.clinicasalud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo2.clinicasalud.validator.coordinates.CoordinatesConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "consultorios")
public class Consultorio {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="consultorio_id", columnDefinition = "BIGINT")
    private long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre", length = 100)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Size(max=2000)
    private String descripcion;

    @NotBlank(message = "La ubicación es obligatoria")
    @Column(name = "ubicacion", columnDefinition = "TEXT")
    @Size(max=2000)
    private String ubicacion;

    @Column(name = "imagen")
    @Size(min=2, max=255)
    private String imagen;

    @CoordinatesConstraint
    @Column(name = "latitud", precision = 11, scale=8)
    private BigDecimal latitud;

    @CoordinatesConstraint
    @Column(name = "longitud", precision = 11, scale=8)
    private BigDecimal longitud;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "consultorio")
    private List<Cita> citas;

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

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }
}
