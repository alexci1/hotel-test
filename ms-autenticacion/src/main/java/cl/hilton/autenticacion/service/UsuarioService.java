package cl.hilton.autenticacion.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public UsuarioResponse create(UsuarioRequest request) {
        validarEmailUnico(request.getEmail());

        Rol rol = rolRepository.findByCodigo(request.getRol())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + request.getRol()));

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setRol(rol);
        usuario.setActivo(true);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioGuardado);
    }

    public UsuarioResponse update(Long id, UsuarioRequest request) {
        Usuario usuario = getUsuarioById(id);

        if (!usuario.getEmail().equalsIgnoreCase(request.getEmail())) {
            validarEmailUnico(request.getEmail());
        }

        Rol rol = rolRepository.findByCodigo(request.getRol())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + request.getRol()));

        usuarioMapper.updateEntity(request, usuario);
        usuario.setRol(rol);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioActualizado);
    }

    public void deleteById(Long id) {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.delete(usuario);
    }

    public UsuarioResponse activar(Long id) {
        Usuario usuario = getUsuarioById(id);
        usuario.setActivo(true);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioActualizado);
    }

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

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + email);
        }
    }
}