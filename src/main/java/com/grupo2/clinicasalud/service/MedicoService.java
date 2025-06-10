package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    MedicoRepository repository;

    @Autowired
    public MedicoService(MedicoRepository repository){
        this.repository = repository;
    }

    public boolean existeConsultorioPorId(long id) { return repository.existsById(id); }

    public Medico dameConsultorioPorId(long id){
        return repository.findById(id).orElse(null);
    }

    public void guardarConsultorio(Medico o){
        repository.save(o);
    }

    public List<Medico> dameMedicos(){
        return repository.findAll();
    }

    public void eliminarMedico(Medico o){
        repository.deleteById(o.getId());
    }
}
