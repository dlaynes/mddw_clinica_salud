package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    private final PacienteRepository repository;

    @Autowired
    public PacienteService(PacienteRepository repository){
        this.repository = repository;
    }

    public Paciente damePacientePorId(long id){
        return repository.findById(id).orElse(null);
    }

    public Paciente damePacientePorEmail(String email){
        return repository.findByEmail(email).orElse(null);
    }
}
