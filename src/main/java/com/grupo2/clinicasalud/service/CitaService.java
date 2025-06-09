package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Cita;
import com.grupo2.clinicasalud.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaService {
    private final CitaRepository repository;

    @Autowired
    public CitaService(CitaRepository repository){
        this.repository = repository;
    }

    public Cita dameCitaPorId(long id){
        return repository.findById(id).orElse(null);
    }

    public void guardarCita(Cita cita){
        repository.save(cita);
    }

    public List<Cita> dameCitas(){
        return repository.findAll();
    }

    public void eliminarCita(Cita cita){
        repository.deleteById(cita.getId());
    }
}
