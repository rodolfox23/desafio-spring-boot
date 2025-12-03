package com.bancobci.entrevista.dto;


import com.bancobci.entrevista.entity.tarea.EstadoTarea;

public record TareaDto(Long id,
                       String titulo,
                       String descripcion,
                       Long userId,
                       EstadoTarea estado) {
}
