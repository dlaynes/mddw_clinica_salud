package com.grupo2.clinicasalud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "consultas")
public class Consulta {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="consulta_id", columnDefinition = "BIGINT")
    private long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre_completo")
    @Size(min=2, max=255)
    private String nombreCompleto;

    @NotBlank(message = "El email es obligatorio")
    @Size(min = 5, max = 150, message = "El email debe tener entre 5 y 150 caracteres")
    @Email
    @Column(name = "email", length = 150)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 5, max = 25, message = "El teléfono debe tener entre 5 y 25 caracteres")
    @Column(name = "telefono", length = 25)
    private String telefono;

    @NotBlank(message = "El contenido de la consulta es obligatorio")
    @Column(name = "comentario", columnDefinition="TEXT")
    @Size(min=2, max=2000, message = "El comentario debe tener entre 2 y 2000 caracteres")
    private String comentario;

    @Column(name= "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
