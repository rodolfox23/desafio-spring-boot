package com.previred.entrevista.controller;

import com.previred.entrevista.entity.tarea.EstadoTarea;
import com.previred.entrevista.service.EstadoTareaService;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EstadoTareaControllerTest {

    @Mock
    private EstadoTareaService estadoTareaService;

    @InjectMocks
    private EstadoTareaController estadoTareaController;

    @Test
    void testGetAllEstados() {
        List<EstadoTarea> estados = new ArrayList<>();
        estados.add(new EstadoTarea(1L, "Estado 1"));
        estados.add(new EstadoTarea(2L, "Estado 2"));

        when(estadoTareaService.getAllEstados()).thenReturn(estados);

        ResponseEntity<List<EstadoTarea>> response = estadoTareaController.getAllEstados();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(estados, response.getBody());
    }

    @Test
    void testGetEstadoById() {
        EstadoTarea estado = new EstadoTarea(1L, "Estado 1");

        when(estadoTareaService.getEstadoById(1L)).thenReturn(estado);

        ResponseEntity<EstadoTarea> response = estadoTareaController.getEstadoById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(estado, response.getBody());
    }

    @Test
    void testGetEstadoById_NotFound() {
        when(estadoTareaService.getEstadoById(1L)).thenReturn(null);

        ResponseEntity<EstadoTarea> response = estadoTareaController.getEstadoById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateEstado() {
        EstadoTarea estado = new EstadoTarea(1L, "Estado 1");

        when(estadoTareaService.createEstado(any())).thenReturn(estado);

        ResponseEntity<EstadoTarea> response = estadoTareaController.createEstado(estado);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(estado, response.getBody());
    }

    @Test
    void testUpdateEstado() {
        EstadoTarea estado = new EstadoTarea(1L, "Estado 1");
        EstadoTarea updatedEstado = new EstadoTarea(1L, "Estado 1 modificado");

        when(estadoTareaService.updateEstado(eq(1L), any())).thenReturn(updatedEstado);

        ResponseEntity<EstadoTarea> response = estadoTareaController.updateEstado(1L, updatedEstado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEstado, response.getBody());
    }

    @Test
    void testUpdateEstado_NotFound() {
        EstadoTarea estado = new EstadoTarea(1L, "Estado 1");

        when(estadoTareaService.updateEstado(eq(1L), any())).thenReturn(null);

        ResponseEntity<EstadoTarea> response = estadoTareaController.updateEstado(1L, estado);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteEstado() {
        ResponseEntity<Void> response = estadoTareaController.deleteEstado(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(estadoTareaService, times(1)).deleteEstado(1L);
    }
}
