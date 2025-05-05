package com.previred.entrevista.controller;

import com.previred.entrevista.entity.tarea.EstadoTarea;
import com.previred.entrevista.service.EstadoTareaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
public class EstadoTareaController {

    private final EstadoTareaService estadoTareaService;

    public EstadoTareaController(EstadoTareaService estadoTareaService) {
        this.estadoTareaService = estadoTareaService;
    }

    @GetMapping
    public ResponseEntity<List<EstadoTarea>> getAllEstados() {
        List<EstadoTarea> estados = estadoTareaService.getAllEstados();
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoTarea> getEstadoById(@PathVariable Long id) {
        EstadoTarea estado = estadoTareaService.getEstadoById(id);
        if (estado != null) {
            return new ResponseEntity<>(estado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<EstadoTarea> createEstado(@RequestBody EstadoTarea estadoTarea) {
        EstadoTarea createdEstado = estadoTareaService.createEstado(estadoTarea);
        return new ResponseEntity<>(createdEstado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoTarea> updateEstado(@PathVariable Long id, @RequestBody EstadoTarea estadoDetails) {
        EstadoTarea updatedEstado = estadoTareaService.updateEstado(id, estadoDetails);
        if (updatedEstado != null) {
            return new ResponseEntity<>(updatedEstado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable Long id) {
        estadoTareaService.deleteEstado(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
