package cl.hilton.autenticacion.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.autenticacion.dto.UsuarioRequest;
import cl.hilton.autenticacion.dto.UsuarioResponse;
import cl.hilton.autenticacion.mapper.UsuarioMapper;
import cl.hilton.autenticacion.model.Rol;
import cl.hilton.autenticacion.model.Usuario;
import cl.hilton.autenticacion.repository.RolRepository;
import cl.hilton.autenticacion.repository.UsuarioRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;

    public List<UsuarioResponse> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    public UsuarioResponse findById(Long id) {
        Usuario usuario = getUsuarioById(id);
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse findByEmail(String email) {
        String emailValido = validarTexto(email, "email");

        Usuario usuario = usuarioRepository.findByEmail(emailValido)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + emailValido));

        return usuarioMapper.toResponse(usuario);
    }

    public List<UsuarioResponse> findByRolCodigo(String rolCodigo) {
        String codigo = validarTexto(rolCodigo, "rolCodigo");
        return usuarioMapper.toResponseList(usuarioRepository.findByRolCodigo(codigo));
    }

    public List<UsuarioResponse> findByActivo(Boolean activo) {
        Boolean estado = validarBoolean(activo, "activo");
        return usuarioMapper.toResponseList(usuarioRepository.findByActivo(estado));
    }

    public List<UsuarioResponse> findByCreadoEn(LocalDate creadoEn) {
        LocalDate fecha = validarFecha(creadoEn, "creadoEn");
        return usuarioMapper.toResponseList(usuarioRepository.findByCreadoEn(fecha));
    }

    public List<UsuarioResponse> findByUltimoAcceso(LocalDate ultimoAcceso) {
        LocalDate fecha = validarFecha(ultimoAcceso, "ultimoAcceso");
        return usuarioMapper.toResponseList(usuarioRepository.findByUltimoAcceso(fecha));
    }

    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        String email = validarTexto(request.getEmail(), "email");
        String codigoRol = validarTexto(request.getRol(), "rol");

        validarEmailUnico(email);

        Rol rol = getRolByCodigo(codigoRol);

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setRol(rol);
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : true);
        usuario.setCreadoEn(LocalDate.now());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioGuardado);
    }

    @Transactional
    public UsuarioResponse update(Long id, UsuarioRequest request) {
        Long usuarioId = validarId(id);
        String email = validarTexto(request.getEmail(), "email");
        String codigoRol = validarTexto(request.getRol(), "rol");

        Usuario usuario = getUsuarioById(usuarioId);
        Boolean activoActual = usuario.getActivo();

        if (!usuario.getEmail().equalsIgnoreCase(email)) {
            validarEmailUnico(email);
        }

        Rol rol = getRolByCodigo(codigoRol);

        usuarioMapper.updateEntity(request, usuario);
        usuario.setRol(rol);
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long usuarioId = validarId(id);
        getUsuarioById(usuarioId);
        usuarioRepository.deleteById(usuarioId);
    }

    @Transactional
    public UsuarioResponse activar(Long id) {
        Usuario usuario = getUsuarioById(id);
        usuario.setActivo(true);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioActualizado);
    }

    @Transactional
    public UsuarioResponse desactivar(Long id) {
        Usuario usuario = getUsuarioById(id);
        usuario.setActivo(false);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioActualizado);
    }

    private Usuario getUsuarioById(Long id) {
        Long usuarioId = validarId(id);

        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + usuarioId));
    }

    private Rol getRolByCodigo(String codigoRol) {
        String codigo = validarTexto(codigoRol, "rol");

        return rolRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + codigo));
    }

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + email);
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private LocalDate validarFecha(LocalDate valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
    }

    private Boolean validarBoolean(Boolean valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
