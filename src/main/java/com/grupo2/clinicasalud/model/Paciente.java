package com.grupo2.clinicasalud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo2.clinicasalud.model.converter.EstadoCivilAttributeConverter;
import com.grupo2.clinicasalud.model.converter.GeneroAttributeConverter;
import com.grupo2.clinicasalud.model.converter.TipoDocumentoAttributeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="paciente_id", columnDefinition = "BIGINT")
    private long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 150, message = "El apellido debe tener entre 2 y 150 caracteres")
    @Column(name = "apellido", length = 150)
    private String apellido;

    @NotNull(message = "El tipo de documento es obligatorio")
    @Column(name = "tipo_documento", length = 1)
    @Convert(converter = TipoDocumentoAttributeConverter.class)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 8, max=16, message = "El número de documento debe tener una longitud válida")
    @Column(name = "numero_documento", length = 16)
    private String numeroDocumento;

    @NotBlank(message = "El email es obligatorio")
    @Size(min = 5, max = 150, message = "El email debe tener entre 2 y 150 caracteres")
    @Email(message = "El email indicado no es válido")
    @Column(name = "email", length = 150)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 5, max = 25, message = "El teléfono debe tener entre 5 y 25 caracteres")
    @Column(name = "telefono", length = 25)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    @NotNull(message = "El género es obligatorio")
    @Column(name = "genero", length = 1)
    @Convert(converter = GeneroAttributeConverter.class)
    private Genero genero;

    @NotNull(message = "El estado civil es obligatorio")
    @Column(name = "estado_civil", length = 1)
    @Convert(converter = EstadoCivilAttributeConverter.class)
    private EstadoCivil estadoCivil;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente")
    private List<Cita> citas;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name="fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    public Paciente(){

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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
