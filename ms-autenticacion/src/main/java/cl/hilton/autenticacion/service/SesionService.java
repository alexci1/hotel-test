package cl.hilton.autenticacion.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.autenticacion.dto.SesionRequest;
import cl.hilton.autenticacion.dto.SesionResponse;
import cl.hilton.autenticacion.mapper.SesionMapper;
import cl.hilton.autenticacion.model.Sesion;
import cl.hilton.autenticacion.model.Usuario;
import cl.hilton.autenticacion.repository.SesionRepository;
import cl.hilton.autenticacion.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
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
        Sesion sesion = sesionRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new EntityNotFoundException("Sesion no encontrada con token hash: " + tokenHash));

        return sesionMapper.toResponse(sesion);
    }

    public SesionResponse findByUsuarioEmail(String usuarioEmail) {
        Sesion sesion = sesionRepository.findByUsuarioEmail(usuarioEmail)
                .orElseThrow(() -> new EntityNotFoundException("Sesion no encontrada para usuario: " + usuarioEmail));

        return sesionMapper.toResponse(sesion);
    }

    public List<SesionResponse> findByInvalidada(Boolean invalidada) {
        return sesionMapper.toResponseList(sesionRepository.findByInvalidada(invalidada));
    }

    public List<SesionResponse> findActivas() {
        return sesionMapper.toResponseList(sesionRepository.findByInvalidadaFalse());
    }

    @Transactional
    public SesionResponse create(SesionRequest request) {
        validarTokenHashUnico(request.getTokenHash());

        if (sesionRepository.existsByUsuarioEmail(request.getUsuarioEmail())) {
            throw new IllegalArgumentException("Ya existe una sesion para el usuario: " + request.getUsuarioEmail());
        }

        Usuario usuario = usuarioRepository.findByEmail(request.getUsuarioEmail())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + request.getUsuarioEmail()));

        Sesion sesion = sesionMapper.toEntity(request);
        sesion.setUsuario(usuario);
        sesion.setCreadaEn(request.getCreadaEn() != null ? request.getCreadaEn() : LocalDate.now());
        sesion.setInvalidada(request.getInvalidada() != null ? request.getInvalidada() : false);

        Sesion sesionGuardada = sesionRepository.save(sesion);

        return sesionMapper.toResponse(sesionGuardada);
    }

    @Transactional
    public SesionResponse update(Long id, SesionRequest request) {
        Sesion sesion = getSesionById(id);
        Boolean invalidadaActual = sesion.getInvalidada();

        if (!sesion.getTokenHash().equals(request.getTokenHash())) {
            validarTokenHashUnico(request.getTokenHash());
        }

        if (!sesion.getUsuario().getEmail().equalsIgnoreCase(request.getUsuarioEmail())
                && sesionRepository.existsByUsuarioEmail(request.getUsuarioEmail())) {
            throw new IllegalArgumentException("Ya existe una sesion para el usuario: " + request.getUsuarioEmail());
        }

        Usuario usuario = usuarioRepository.findByEmail(request.getUsuarioEmail())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + request.getUsuarioEmail()));

        sesionMapper.updateEntity(request, sesion);
        sesion.setUsuario(usuario);
        sesion.setInvalidada(request.getInvalidada() != null ? request.getInvalidada() : invalidadaActual);

        Sesion sesionActualizada = sesionRepository.save(sesion);

        return sesionMapper.toResponse(sesionActualizada);
    }

    @Transactional
    public SesionResponse invalidar(Long id) {
        Sesion sesion = getSesionById(id);
        sesion.setInvalidada(true);

        Sesion sesionActualizada = sesionRepository.save(sesion);

        return sesionMapper.toResponse(sesionActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long idValido = validarId(id);
        getSesionById(idValido);
        sesionRepository.deleteById(idValido);
    }

    private Sesion getSesionById(Long id) {
        Long idValido = validarId(id);

        return sesionRepository.findById(idValido)
                .orElseThrow(() -> new EntityNotFoundException("Sesion no encontrada con id: " + idValido));
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        return Long.valueOf(id.longValue());
    }

    private void validarTokenHashUnico(String tokenHash) {
        if (sesionRepository.existsByTokenHash(tokenHash)) {
            throw new IllegalArgumentException("Ya existe una sesion con el token hash indicado");
        }
    }
}