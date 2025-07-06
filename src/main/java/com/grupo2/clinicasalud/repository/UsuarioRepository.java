package com.grupo2.clinicasalud.repository;

import com.grupo2.clinicasalud.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email); // Necesario para validar unicidad

    Optional<Usuario> findByEmail(String email); //Aquí era el error no encontraba directamente al Usuario y se tuvo que colocar toda la ruta
}

