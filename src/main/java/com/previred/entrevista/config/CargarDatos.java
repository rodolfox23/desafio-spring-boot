package com.previred.entrevista.config;

import com.previred.entrevista.dao.EstadoTareaRepository;
import com.previred.entrevista.dao.UsuarioRepository;
import com.previred.entrevista.entity.tarea.EstadoTarea;
import com.previred.entrevista.entity.usuario.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargarDatos implements CommandLineRunner {

    private final EstadoTareaRepository estadoTareaRepository;
    private final UsuarioRepository usuarioRepository;
    public CargarDatos(EstadoTareaRepository estadoTareaRepository,
                       UsuarioRepository usuarioRepository) {
        this.estadoTareaRepository  = estadoTareaRepository;
        this.usuarioRepository      = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (estadoTareaRepository.count() == 0) {
            EstadoTarea nueva = new EstadoTarea();
            nueva.setEstado("Nueva");
            estadoTareaRepository.save(nueva);
            EstadoTarea pendiente = new EstadoTarea();
            pendiente.setEstado("Pendiente");
            estadoTareaRepository.save(pendiente);
            EstadoTarea enProgreso = new EstadoTarea();
            enProgreso.setEstado("En Progreso");
            estadoTareaRepository.save(enProgreso);
            EstadoTarea completada = new EstadoTarea();
            completada.setEstado("Completada");
            estadoTareaRepository.save(completada);
        }

        if (usuarioRepository.count() == 0) {
            Usuario usuario1 = new Usuario();
            usuario1.setNombre("Juan Perez");
            usuario1.setEmail("juan.perez@example.com");
            usuario1.setPassword("123456");
            usuarioRepository.save(usuario1);

            Usuario usuario2 = new Usuario();
            usuario2.setNombre("Maria perez");
            usuario2.setEmail("maria.perez@example.com");
            usuario2.setPassword("123456");
            usuarioRepository.save(usuario2);
        }
    }
}
