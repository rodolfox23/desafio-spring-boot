package com.previred.entrevista.controller;

import com.previred.entrevista.dto.TareaDto;
import com.previred.entrevista.service.TareaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class TareaControllerTest {

    @Mock
    private TareaService tareaService;

    @InjectMocks
    private TareaController tareaController;

    @Test
    void testObtenerTodasLasTareas() {
        List<TareaDto> tareas = new ArrayList<>();
        tareas.add(new TareaDto(1L, "Tarea 1", "Descripción tarea 1", 1L, null));
        tareas.add(new TareaDto(2L, "Tarea 2", "Descripción tarea 2", 2L, null));

        when(tareaService.obtenerTodasLasTareas()).thenReturn(tareas);

        List<TareaDto> response = tareaController.obtenerTodasLasTareas();

        assertEquals(tareas, response);
    }

    @Test
    void testCrearTarea() {
        TareaDto tareaDto = new TareaDto(1L, "Nueva Tarea", "Descripción de la nueva tarea", 1L, null);

        when(tareaService.crearTarea(tareaDto)).thenReturn(tareaDto);

        ResponseEntity<TareaDto> response = tareaController.crearTarea(tareaDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tareaDto, response.getBody());
    }

    @Test
    void testGetTareaById() {
        TareaDto tareaDto = new TareaDto(1L, "Tarea 1", "Descripción tarea 1", 1L, null);

        when(tareaService.getTareaById(1L)).thenReturn(tareaDto);

        ResponseEntity<TareaDto> response = tareaController.getTareaById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tareaDto, response.getBody());
    }

    @Test
    void testActualizarTarea() {
        TareaDto tareaDto = new TareaDto(1L, "Tarea 1 Actualizada", "Descripción tarea 1 actualizada", 1L, null);

        when(tareaService.updateTask(1L, tareaDto)).thenReturn(tareaDto);

        ResponseEntity<TareaDto> response = tareaController.actualizarTarea(1L, tareaDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tareaDto, response.getBody());
    }

    @Test
    void testBorrarTarea() {
        ResponseEntity<Void> response = tareaController.borrarTarea(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tareaService, times(1)).deleteTarea(1L);
    }
}
