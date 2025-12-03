package com.bancobci.entrevista.config;

import com.bancobci.entrevista.dao.EstadoTareaRepository;
import com.bancobci.entrevista.dao.UsuarioRepository;
import com.bancobci.entrevista.entity.tarea.EstadoTarea;
import com.bancobci.entrevista.entity.usuario.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CargarDatos implements CommandLineRunner {

    private final EstadoTareaRepository estadoTareaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CargarDatos(EstadoTareaRepository estadoTareaRepository,
                       UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder) {
        this.estadoTareaRepository  = estadoTareaRepository;
        this.usuarioRepository      = usuarioRepository;
        this.passwordEncoder        = passwordEncoder;
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

        usuarioRepository.findByEmail("juan.perez@example.com").ifPresent(usuarioRepository::delete);
        usuarioRepository.findByEmail("maria.perez@example.com").ifPresent(usuarioRepository::delete);

        LocalDateTime now = LocalDateTime.now();

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan Perez");
        usuario1.setEmail("juan.perez@example.com");
        usuario1.setPassword(passwordEncoder.encode("Password123"));
        usuario1.setActive(true);
        usuario1.setCreated(now);
        usuario1.setModified(now);
        usuario1.setLastLogin(now);
        usuarioRepository.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Maria perez");
        usuario2.setEmail("maria.perez@example.com");
        usuario2.setPassword(passwordEncoder.encode("Password123"));
        usuario2.setActive(true);
        usuario2.setCreated(now);
        usuario2.setModified(now);
        usuario2.setLastLogin(now);
        usuarioRepository.save(usuario2);
    }
}