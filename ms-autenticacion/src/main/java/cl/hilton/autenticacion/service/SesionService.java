package cl.hilton.autenticacion.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.autenticacion.dto.SesionRequest;
import cl.hilton.autenticacion.dto.SesionResponse;
import cl.hilton.autenticacion.mapper.SesionMapper;
import cl.hilton.autenticacion.model.Sesion;
import cl.hilton.autenticacion.model.Usuario;
import cl.hilton.autenticacion.repository.SesionRepository;
import cl.hilton.autenticacion.repository.UsuarioRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SesionService {

    private final SesionRepository sesionRepository;
    private final UsuarioRepository usuarioRepository;
    private final SesionMapper sesionMapper;

    public List<SesionResponse> findAll() {
        return sesionMapper.toResponseList(sesionRepository.findAll());
    }

    public SesionResponse findById(Long id) {
        Sesion sesion = getSesionById(id);
        return sesionMapper.toResponse(sesion);
    }

    public SesionResponse findByTokenHash(String tokenHash) {
        String token = validarTexto(tokenHash, "tokenHash");

        Sesion sesion = sesionRepository.findByTokenHash(token)
                .orElseThrow(() -> new EntityNotFoundException("Sesion no encontrada con token hash: " + token));

        return sesionMapper.toResponse(sesion);
    }

    public SesionResponse findByUsuarioEmail(String usuarioEmail) {
        String email = validarTexto(usuarioEmail, "usuarioEmail");

        Sesion sesion = sesionRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Sesion no encontrada para usuario: " + email));

        return sesionMapper.toResponse(sesion);
    }

    public List<SesionResponse> findByInvalidada(Boolean invalidada) {
        Boolean estado = validarBoolean(invalidada, "invalidada");
        return sesionMapper.toResponseList(sesionRepository.findByInvalidada(estado));
    }

    public List<SesionResponse> findActivas() {
        return sesionMapper.toResponseList(sesionRepository.findByInvalidadaFalse());
    }

    @Transactional
    public SesionResponse create(SesionRequest request) {
        String tokenHash = validarTexto(request.getTokenHash(), "tokenHash");
        String usuarioEmail = validarTexto(request.getUsuarioEmail(), "usuarioEmail");

        validarTokenHashUnico(tokenHash);

        if (sesionRepository.existsByUsuarioEmail(usuarioEmail)) {
            throw new IllegalArgumentException("Ya existe una sesion para el usuario: " + usuarioEmail);
        }

        Usuario usuario = getUsuarioByEmail(usuarioEmail);

        Sesion sesion = sesionMapper.toEntity(request);
        sesion.setUsuario(usuario);
        sesion.setCreadaEn(request.getCreadaEn() != null ? request.getCreadaEn() : LocalDate.now());
        sesion.setInvalidada(request.getInvalidada() != null ? request.getInvalidada() : false);

        Sesion sesionGuardada = sesionRepository.save(sesion);

        return sesionMapper.toResponse(sesionGuardada);
    }

    @Transactional
    public SesionResponse update(Long id, SesionRequest request) {
        Long sesionId = validarId(id);
        String tokenHash = validarTexto(request.getTokenHash(), "tokenHash");
        String usuarioEmail = validarTexto(request.getUsuarioEmail(), "usuarioEmail");

        Sesion sesion = getSesionById(sesionId);
        Boolean invalidadaActual = sesion.getInvalidada();

        if (!sesion.getTokenHash().equals(tokenHash)) {
            validarTokenHashUnico(tokenHash);
        }

        if (!sesion.getUsuario().getEmail().equalsIgnoreCase(usuarioEmail)
                && sesionRepository.existsByUsuarioEmail(usuarioEmail)) {
            throw new IllegalArgumentException("Ya existe una sesion para el usuario: " + usuarioEmail);
        }

        Usuario usuario = getUsuarioByEmail(usuarioEmail);

        sesionMapper.updateEntity(request, sesion);
        sesion.setUsuario(usuario);
        sesion.setInvalidada(request.getInvalidada() != null ? request.getInvalidada() : invalidadaActual);

        Sesion sesionActualizada = sesionRepository.save(sesion);

        return sesionMapper.toResponse(sesionActualizada);
    }

    @Transactional
    public SesionResponse invalidar(Long id) {
        Long sesionId = validarId(id);

        Sesion sesion = getSesionById(sesionId);
        sesion.setInvalidada(true);

        Sesion sesionActualizada = sesionRepository.save(sesion);

        return sesionMapper.toResponse(sesionActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long sesionId = validarId(id);
        getSesionById(sesionId);
        sesionRepository.deleteById(sesionId);
    }

    private Sesion getSesionById(Long id) {
        Long sesionId = validarId(id);

        return sesionRepository.findById(sesionId)
                .orElseThrow(() -> new EntityNotFoundException("Sesion no encontrada con id: " + sesionId));
    }

    private Usuario getUsuarioByEmail(String usuarioEmail) {
        String email = validarTexto(usuarioEmail, "usuarioEmail");

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + email));
    }

    private void validarTokenHashUnico(String tokenHash) {
        if (sesionRepository.existsByTokenHash(tokenHash)) {
            throw new IllegalArgumentException("Ya existe una sesion con el token hash indicado");
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
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
