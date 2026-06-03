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
import jakarta.persistence.EntityNotFoundException;
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
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + email));

        return usuarioMapper.toResponse(usuario);
    }

    public List<UsuarioResponse> findByRolCodigo(String rolCodigo) {
        return usuarioMapper.toResponseList(usuarioRepository.findByRolCodigo(rolCodigo));
    }

    public List<UsuarioResponse> findByActivo(Boolean activo) {
        return usuarioMapper.toResponseList(usuarioRepository.findByActivo(activo));
    }

    public List<UsuarioResponse> findByCreadoEn(LocalDate creadoEn) {
        return usuarioMapper.toResponseList(usuarioRepository.findByCreadoEn(creadoEn));
    }

    public List<UsuarioResponse> findByUltimoAcceso(LocalDate ultimoAcceso) {
        return usuarioMapper.toResponseList(usuarioRepository.findByUltimoAcceso(ultimoAcceso));
    }

    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        validarEmailUnico(request.getEmail());

        Rol rol = getRolByCodigo(request.getRol());

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setRol(rol);
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : true);
        usuario.setCreadoEn(LocalDate.now());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioGuardado);
    }

    @Transactional
    public UsuarioResponse update(Long id, UsuarioRequest request) {
        Usuario usuario = getUsuarioById(id);
        Boolean activoActual = usuario.getActivo();

        if (!usuario.getEmail().equalsIgnoreCase(request.getEmail())) {
            validarEmailUnico(request.getEmail());
        }

        Rol rol = getRolByCodigo(request.getRol());

        usuarioMapper.updateEntity(request, usuario);
        usuario.setRol(rol);
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        getUsuarioById(id);
        usuarioRepository.deleteById(id);
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
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
    }

    private Rol getRolByCodigo(String codigoRol) {
        return rolRepository.findByCodigo(codigoRol)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + codigoRol));
    }

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + email);
        }
    }
}
