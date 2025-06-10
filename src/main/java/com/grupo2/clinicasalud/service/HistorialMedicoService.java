package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.HistorialMedico;
import com.grupo2.clinicasalud.repository.HistorialMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class HistorialMedicoService {
    HistorialMedicoRepository repository;

    @Autowired
    public HistorialMedicoService(HistorialMedicoRepository repository){
        this.repository = repository;
    }

    public boolean existeHistorialPorId(long id) { return repository.existsById(id); }

    public HistorialMedico dameHistorialPorId(long id){
        return repository.findById(id).orElse(null);
    }

    public void guardarHistorial(HistorialMedico o){
        repository.save(o);
    }

    public void eliminarHistorial(HistorialMedico o){
        repository.deleteById(o.getId());
    }

}
