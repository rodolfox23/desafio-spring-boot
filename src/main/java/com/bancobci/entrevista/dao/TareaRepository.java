package com.bancobci.entrevista.dao;

import com.bancobci.entrevista.entity.tarea.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
