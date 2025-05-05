package com.previred.entrevista.controller;

import com.previred.entrevista.dto.TareaDto;
import com.previred.entrevista.service.TareaService;
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
@RequestMapping("/api/tareas")
public class TareaController {
    private final TareaService tareaService;
    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }
    @GetMapping
    public List<TareaDto> obtenerTodasLasTareas() {
        return tareaService.obtenerTodasLasTareas();
    }
    @PostMapping
    public ResponseEntity<TareaDto> crearTarea(@RequestBody TareaDto tareDto) {
        return ResponseEntity.ok(tareaService.crearTarea(tareDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TareaDto> getTareaById(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.getTareaById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TareaDto> actualizarTarea(@PathVariable Long id,
                                                    @RequestBody TareaDto taskDto) {
        return ResponseEntity.ok(tareaService.updateTask(id, taskDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarTarea(@PathVariable Long id) {
        tareaService.deleteTarea(id);
        return ResponseEntity.noContent().build();
    }
}
