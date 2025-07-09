package com.grupo2.clinicasalud.model.form.admin;

import com.grupo2.clinicasalud.model.Rol;
import com.grupo2.clinicasalud.validator.passwordmatch.PasswordMatchContraint;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

@PasswordMatchContraint
public class UsuarioForm {

    private Long id;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    @Size(max=150, message = "El email no puede tener más de 150 caracteres")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial: @$!%*?&"
    )
    private String password;

    private String passwordConfirm;

    private Set<Rol> roles = new HashSet<>();

    @Size(max = 150, message = "El nombre debe tener menos de 150 caracteres")
    private String nombre;

    @Size(max = 150, message = "El apellido debe tener menos de 150 caracteres")
    private String apellido;

    @Size(max = 25, message = "El teléfono debe tener menos de 25 caracteres")
    private String telefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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

    public void setApellido(String apellidos) {
        this.apellido = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
