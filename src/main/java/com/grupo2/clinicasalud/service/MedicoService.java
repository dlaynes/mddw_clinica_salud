package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Especialidad;
import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository repository){
        this.medicoRepository = repository;
    }

    public boolean existeMedicoPorId(long id) { return medicoRepository.existsById(id); }

    public Medico dameMedicoPorId(long id){
        return medicoRepository.findById(id).orElse(null);
    }

    public List<Medico> dameMedicosPorEspecialidad(Especialidad especialidad){
        return medicoRepository.dameMedicosPorEspecialidad(especialidad.getId());
    }

    public void guardarMedico(Medico o){
        medicoRepository.save(o);
    }

    public List<Medico> dameMedicos(){
        return medicoRepository.findAll();
    }

    public void eliminarMedico(Medico o){
        medicoRepository.deleteById(o.getId());
    }

    public long countMedicos() {
        return medicoRepository.count();
    }
}
