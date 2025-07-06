package com.grupo2.clinicasalud.validator.passwordmatch;

import com.grupo2.clinicasalud.model.form.admin.UsuarioForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatchContraint, Object> {
    @Override
    public void initialize(PasswordMatchContraint constraintAnnotation) {
        // Initialization logic (if needed)
    }
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // Cast the value to the target object (e.g., a form or DTO)
        UsuarioForm usuarioForm = (UsuarioForm) value;

        // Si el usuario no ha llenado una contraseña, ignoramos la regla
        if(usuarioForm.getPassword() == null){
            return true;
        }

        // Validate if the password and confirmPassword match
        return usuarioForm.getPassword().equals(usuarioForm.getPasswordConfirm());
    }
}