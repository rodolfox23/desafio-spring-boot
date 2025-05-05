package com.previred.entrevista.service;

import com.previred.entrevista.dao.EstadoTareaRepository;
import com.previred.entrevista.entity.tarea.EstadoTarea;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EstadoTareaServiceTest {

    @Mock
    private EstadoTareaRepository estadoTareaRepository;

    @InjectMocks
    private EstadoTareaService estadoTareaService;

    @Test
    void testGetAllEstados() {
        List<EstadoTarea> estados = new ArrayList<>();
        estados.add(new EstadoTarea(1L, "Estado 1"));
        estados.add(new EstadoTarea(2L, "Estado 2"));

        when(estadoTareaRepository.findAll()).thenReturn(estados);

        List<EstadoTarea> result = estadoTareaService.getAllEstados();

        assertEquals(estados, result);
    }

    @Test
    void testGetEstadoById_Found() {
        Long id = 1L;
        EstadoTarea estado = new EstadoTarea(id, "Estado 1");

        when(estadoTareaRepository.findById(id)).thenReturn(Optional.of(estado));

        EstadoTarea result = estadoTareaService.getEstadoById(id);

        assertEquals(estado, result);
    }

    @Test
    void testGetEstadoById_NotFound() {
        Long id = 1L;

        when(estadoTareaRepository.findById(id)).thenReturn(Optional.empty());

        EstadoTarea result = estadoTareaService.getEstadoById(id);

        assertNull(result);
    }

    @Test
    void testCreateEstado() {
        EstadoTarea estado = new EstadoTarea(1L, "Estado 1");

        when(estadoTareaRepository.save(estado)).thenReturn(estado);

        EstadoTarea result = estadoTareaService.createEstado(estado);

        assertEquals(estado, result);
    }

    @Test
    void testUpdateEstado_Found() {
        Long id = 1L;
        EstadoTarea estado = new EstadoTarea(id, "Estado 1");
        EstadoTarea updatedEstado = new EstadoTarea(id, "Estado Actualizado");

        when(estadoTareaRepository.findById(id)).thenReturn(Optional.of(estado));
        when(estadoTareaRepository.save(estado)).thenReturn(updatedEstado);

        EstadoTarea result = estadoTareaService.updateEstado(id, updatedEstado);

        assertEquals(updatedEstado, result);
    }

    @Test
    void testUpdateEstado_NotFound() {
        Long id = 1L;
        EstadoTarea updatedEstado = new EstadoTarea(id, "Estado Actualizado");

        when(estadoTareaRepository.findById(id)).thenReturn(Optional.empty());

        EstadoTarea result = estadoTareaService.updateEstado(id, updatedEstado);

        assertNull(result);
    }

    @Test
    void testDeleteEstado() {
        Long id = 1L;

        estadoTareaService.deleteEstado(id);

        verify(estadoTareaRepository, times(1)).deleteById(id);
    }
}
