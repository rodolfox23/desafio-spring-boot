package com.previred.entrevista.service;

import com.previred.entrevista.dao.EstadoTareaRepository;
import com.previred.entrevista.entity.tarea.EstadoTarea;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoTareaService {

    private final EstadoTareaRepository estadoTareaRepository;

    public EstadoTareaService(EstadoTareaRepository estadoTareaRepository) {
        this.estadoTareaRepository = estadoTareaRepository;
    }

    public List<EstadoTarea> getAllEstados() {
        return estadoTareaRepository.findAll();
    }

    public EstadoTarea getEstadoById(Long id) {
        return estadoTareaRepository.findById(id).orElse(null);
    }

    public EstadoTarea createEstado(EstadoTarea estadoTarea) {
        return estadoTareaRepository.save(estadoTarea);
    }

    public EstadoTarea updateEstado(Long id, EstadoTarea estadoDetails) {
        EstadoTarea estado = estadoTareaRepository.findById(id).orElse(null);
        if (estado != null) {
            estado.setEstado(estadoDetails.getEstado());
            return estadoTareaRepository.save(estado);
        }
        return null;
    }

    public void deleteEstado(Long id) {
        estadoTareaRepository.deleteById(id);
    }

}
