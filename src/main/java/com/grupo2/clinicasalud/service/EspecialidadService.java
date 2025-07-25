package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Especialidad;
import com.grupo2.clinicasalud.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {
    private final EspecialidadRepository repository;

    @Autowired
    public EspecialidadService(EspecialidadRepository repository){
        this.repository = repository;
    }

    public Especialidad dameEspecialidadPorId(long id){
        return repository.findById(id).orElse(null);
    }

    public void guardarEspecialidad(Especialidad e){
        repository.save(e);
    }

    public List<Especialidad> dameEspecialidades(){
        return repository.findAll();
    }

    public void eliminarEspecialidad(Long id){
        repository.deleteById(id);
    }

}
