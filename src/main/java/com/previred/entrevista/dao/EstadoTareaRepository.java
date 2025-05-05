package com.previred.entrevista.dao;

import com.previred.entrevista.entity.tarea.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
}
