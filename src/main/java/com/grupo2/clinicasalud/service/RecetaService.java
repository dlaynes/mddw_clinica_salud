package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Receta;

import com.grupo2.clinicasalud.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RecetaService {
    private final RecetaRepository repository;

    @Autowired
    public RecetaService(RecetaRepository repository){
        this.repository = repository;
    }

    public Receta dameRecetaPorId(long id){
        return repository.findById(id).orElse(null);
    }

    public void guardarReceta(Receta e){
        repository.save(e);
    }

    public List<Receta> dameRecetas(){
        return repository.findAll();
    }

    public void eliminarReceta(Receta e){
        repository.deleteById(e.getId());
    }
}
