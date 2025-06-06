package com.grupo2.clinicasalud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "medicos")
public class Medico {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="medico_id")
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 150, message = "El apellido debe tener entre 2 y 150 caracteres")
    @Column(name = "apellido")
    private String apellido;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 8, max=12, message = "El número de documento debe tener una longitud válida")
    @Column(name = "numero_documento")
    private String numeroDocumento;

    @NotBlank(message = "El email es obligatorio")
    @Size(min = 5, max = 150, message = "El email debe tener entre 2 y 150 caracteres")
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 5, max = 25, message = "El teléfono debe tener entre 5 y 25 caracteres")
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @NotBlank(message = "El género es obligatorio")
    @Column(name = "genero")
    private Genero genero;

    @NotBlank(message = "El estado civil es obligatorio")
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;

    public Medico(String id, String nombre, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String email, String apellido, Date fechaCreacion, Genero genero, EstadoCivil estadoCivil) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.telefono = telefono;
        this.email = email;
        this.apellido = apellido;
        this.fechaCreacion = fechaCreacion;
        this.genero = genero;
        this.estadoCivil = estadoCivil;
    }

    public Medico(){

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
