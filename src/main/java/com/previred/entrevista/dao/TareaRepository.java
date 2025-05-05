package com.previred.entrevista.dao;

import com.previred.entrevista.entity.tarea.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
