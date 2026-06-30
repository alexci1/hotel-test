package cl.hilton.autenticacion.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.hilton.autenticacion.dto.LoginRequest;
import cl.hilton.autenticacion.dto.LoginResponse;
import cl.hilton.autenticacion.dto.RegisterRequest;
import cl.hilton.autenticacion.dto.UsuarioResponse;
import cl.hilton.autenticacion.mapper.UsuarioMapper;
import cl.hilton.autenticacion.model.Rol;
import cl.hilton.autenticacion.model.Usuario;
import cl.hilton.autenticacion.repository.RolRepository;
import cl.hilton.autenticacion.repository.UsuarioRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import cl.hilton.common.security.JwtProperties;
import cl.hilton.common.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @Transactional
    public LoginResponse login(LoginRequest request) {

        String correo = request.getEmail();

        Usuario usuario = usuarioRepository.findByEmail(correo)
                .orElseThrow(() -> {
                    log.warn("Intento de login con email inexistente: {}", request.getEmail());
                    return new RuntimeException("Credenciales inválidas");
                });

        if (usuario.getActivo() == null || !usuario.getActivo()) {
            throw new RuntimeException("La cuenta está desactivada. Contacte al administrador.");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getHashPassword())) {
            log.warn("Contraseña incorrecta para: {}", request.getEmail());
            throw new RuntimeException("Credenciales inválidas");
        }

        usuario.setUltimoAcceso(LocalDate.now());
        usuarioRepository.save(usuario);

        String token = jwtTokenProvider.generarToken(
            usuario.getEmail(),
            usuario.getRol().getCodigo(),
            usuario.getNombreCompleto()
        );

        log.info("Login exitoso para: {} con rol: {}", usuario.getEmail(), usuario.getRol().getCodigo());

        return LoginResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .nombre(usuario.getNombreCompleto())
                .rol(usuario.getRol().getCodigo())
                .expiresIn(jwtProperties.getExpirationMs())
                .build();
    }

    @Transactional
    public UsuarioResponse register(RegisterRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + request.getEmail());
        }

        // El rol por defecto en registro público — ajusta el código según tu tabla roles
        Rol rol = rolRepository.findByCodigo("RECEPCION")
                .orElseThrow(() -> new EntityNotFoundException("Rol por defecto no encontrado"));

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .nombreCompleto(request.getNombre() + " " + request.getApellido())
                .hashPassword(passwordEncoder.encode(request.getPassword()))
                .rol(rol)
                .activo(true)
                .creadoEn(LocalDate.now())
                .ultimoAcceso(LocalDate.now())
                .build();

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario registrado: {}", guardado.getEmail());

        return usuarioMapper.toResponse(guardado);
    }
}