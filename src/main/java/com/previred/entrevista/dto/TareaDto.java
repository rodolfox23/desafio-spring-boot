package com.previred.entrevista.dto;


import com.previred.entrevista.entity.tarea.EstadoTarea;

public record TareaDto(Long id,
                       String titulo,
                       String descripcion,
                       Long userId,
                       EstadoTarea estado) {
}
