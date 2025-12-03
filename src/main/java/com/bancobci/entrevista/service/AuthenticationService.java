package com.bancobci.entrevista.service;

import com.bancobci.entrevista.dao.EstadoTareaRepository;
import com.bancobci.entrevista.dao.TokenRepository;
import com.bancobci.entrevista.dao.UsuarioRepository;
import com.bancobci.entrevista.dto.LoginRequest;
import com.bancobci.entrevista.dto.LoginResponse;
import com.bancobci.entrevista.dto.PhoneDto;
import com.bancobci.entrevista.dto.ResponseSignUp;
import com.bancobci.entrevista.dto.TareaDto;
import com.bancobci.entrevista.dto.UsuarioDto;
import com.bancobci.entrevista.entity.tarea.EstadoTarea;
import com.bancobci.entrevista.entity.tarea.Tarea;
import com.bancobci.entrevista.entity.token.Token;
import com.bancobci.entrevista.entity.token.TokenType;
import com.bancobci.entrevista.entity.usuario.Phone;
import com.bancobci.entrevista.entity.usuario.Usuario;
import com.bancobci.entrevista.exceptions.InvalidEmailException;
import com.bancobci.entrevista.exceptions.PasswordPatternException;
import org.springframework.dao.DataIntegrityViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.bancobci.entrevista.utils.Constantes.USUARIO_NO_ENCONTRADO;

@Slf4j
@Service
public class AuthenticationService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final EstadoTareaRepository estadoTareaRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Value("${app.regex.password}")
    private String passwordRegex;

    public AuthenticationService(UsuarioRepository usuarioRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager,
                                 EstadoTareaRepository estadoTareaRepository) {
        this.usuarioRepository      = usuarioRepository;
        this.jwtService             = jwtService;
        this.passwordEncoder        = passwordEncoder;
        this.tokenRepository        = tokenRepository;
        this.authenticationManager  = authenticationManager;
        this.estadoTareaRepository  = estadoTareaRepository;
    }

    public ResponseSignUp signUpUsuario(UsuarioDto usuarioDTO){
        validacion(usuarioDTO);
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new InvalidEmailException("El correo ya registrado");
        }
        try {
            Usuario usuario = mapToUsuario(usuarioDTO);
            Usuario save = usuarioRepository.save(usuario);
            String token = jwtService.generateToken(usuario);
            saveUsuarioToken(save,token);
            return ResponseSignUp.builder()
                    .token(token)
                    .id(save.getUser_id().toString())
                    .created(save.getCreated().toString())
                    .modified(save.getModified().toString())
                    .last_login(save.getLastLogin().toString())
                    .isactive(save.isActive())
                    .build();
        } catch (DataIntegrityViolationException e) {
            log.error("error ejecutando signUp: email duplicado", e);
            throw new InvalidEmailException("El correo ya registrado");
        } catch (Exception e) {
            log.error("error ejecutando signUp", e);
            throw new RuntimeException("Error al procesar el registro del usuario");
        }
    }

    public List<UsuarioDto> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertToDto)
                .toList();
    }

    public UsuarioDto getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null)
            return null;
        return convertToDto(usuario);
    }

    private void validacion(UsuarioDto usuarioDTO) {
        if (!validateEmail(usuarioDTO.getEmail()))
            throw new InvalidEmailException("El email ingresado es incorrecto");

        if (!validatePatterPassword(usuarioDTO.getPassword()))
            throw new PasswordPatternException("La password no sigue la recomendacion de una letra mayuscula , dos numeros y un largo minimo de 6");
    }

    public LoginResponse logInUsusario(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        );
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(USUARIO_NO_ENCONTRADO));
        usuario.setLastLogin(LocalDateTime.now());
        String token = jwtService.generateToken(usuario);
        revokeAllUserTokens(usuario);
        saveUsuarioToken(usuario, token);
        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUser(usuario.getUser_id());
        Usuario save = usuarioRepository.save(usuario);
        return LoginResponse.builder()
                .id(save.getUser_id().toString())
                .name(save.getNombre())
                .isActive(save.isActive())
                .created(save.getCreated().toString())
                .email(save.getEmail())
                .lastLogin(save.getLastLogin().toString())
                .token(allValidTokenByUser.get(0).getToken())
                .build();
    }

    private void revokeAllUserTokens(Usuario user) {
        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUser(user.getUser_id());
        if (allValidTokenByUser.isEmpty())
            return;
        allValidTokenByUser.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(allValidTokenByUser);
    }

    private Usuario mapToUsuario(UsuarioDto usuarioDTO) {
        LocalDateTime now = LocalDateTime.now();
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setActive(true);
        usuario.setCreated(now);
        usuario.setModified(now);
        usuario.setLastLogin(now);
        if (usuarioDTO.getPhones() != null && !usuarioDTO.getPhones().isEmpty()) {
            List<Phone> phones = new ArrayList<>();
            for (PhoneDto phoneDto : usuarioDTO.getPhones()) {
                Phone phone = Phone.builder()
                        .number(phoneDto.getNumber())
                        .citycode(phoneDto.getCitycode())
                        .contrycode(phoneDto.getContrycode())
                        .usuario(usuario)
                        .build();
                phones.add(phone);
            }
            usuario.setPhones(phones);
        }
        return usuario;
    }


    private void saveUsuarioToken(Usuario usuario,
                                  String jwtToken) {
        Token token = Token.builder()
                .user(usuario)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public UsuarioDto updateUsuario(Long id,
                                    UsuarioDto request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(USUARIO_NO_ENCONTRADO));
        Usuario usuarioActualizado = updateUsuario(request, usuario);
        Usuario usuarioActualizadoBd = usuarioRepository.save(usuarioActualizado);
        return convertToDto(usuarioActualizadoBd);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDto patchUsuario(Long id, UsuarioDto request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(USUARIO_NO_ENCONTRADO));
        if (request.getNombre() != null) {
            usuario.setNombre(request.getNombre());
        }
        if (request.getEmail() != null) {
            usuario.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        usuario.setModified(LocalDateTime.now());
        Usuario usuarioActualizadoBd = usuarioRepository.save(usuario);
        return convertToDto(usuarioActualizadoBd);
    }

    private Usuario updateUsuario(UsuarioDto request,
                                  Usuario usuario){
        usuario.setNombre(request.getNombre() != null ? request.getNombre() : usuario.getNombre());
        usuario.setEmail(request.getEmail() != null ? request.getEmail() : usuario.getEmail());
        usuario.setPassword(request.getPassword() != null ?  passwordEncoder.encode(request.getPassword()): usuario.getPassword());
        usuario.setModified(LocalDateTime.now());
        return usuario;

    }

    public UsuarioDto infoUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USUARIO_NO_ENCONTRADO));
        return UsuarioDto.builder()
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .build();

    }
    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean validatePatterPassword(String pass){
        Pattern  PASSWORD_PATTERN = Pattern.compile(passwordRegex);
        if (pass == null) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(pass).matches();
    }
    private UsuarioDto convertToDto(Usuario usuario) {
        List<TareaDto> tareas = new ArrayList<>();
        if (usuario.getTareas() != null){
            for (Tarea tarea : usuario.getTareas()) {
                Optional<EstadoTarea> byId = estadoTareaRepository.findById(tarea.getEstado().getId());
                byId.ifPresent(tarea::setEstado);
                TareaDto tareaDto = new TareaDto(
                        tarea.getId(),
                        tarea.getTitulo(),
                        tarea.getDescripcion(),
                        tarea.getUsuario().getUser_id(),
                        tarea.getEstado()
                );
                tareas.add(tareaDto);
            }
        }
        return UsuarioDto.builder()
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .password("")
                .tareas(tareas)
                .build();
    }
}
