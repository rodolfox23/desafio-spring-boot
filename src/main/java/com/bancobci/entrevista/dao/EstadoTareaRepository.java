package com.bancobci.entrevista.dao;

import com.bancobci.entrevista.entity.tarea.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
}
