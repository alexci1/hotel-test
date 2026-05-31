package cl.hilton.notificaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.notificaciones.dto.EnvioRequest;
import cl.hilton.notificaciones.dto.EnvioResponse;
import cl.hilton.notificaciones.mapper.EnvioMapper;
import cl.hilton.notificaciones.model.Envio;
import cl.hilton.notificaciones.model.Notificacion;
import cl.hilton.notificaciones.repository.EnvioRepository;
import cl.hilton.notificaciones.repository.NotificacionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final NotificacionRepository notificacionRepository;
    private final EnvioMapper envioMapper;

    public List<EnvioResponse> findAll() {
        return envioMapper.toResponseList(envioRepository.findAll());
    }

    public EnvioResponse findById(Long id) {
        Envio envio = getEnvioById(id);
        return envioMapper.toResponse(envio);
    }

    public EnvioResponse findByNotificacionId(Long notificacionId) {
        Envio envio = envioRepository.findByNotificacionId(notificacionId)
                .orElseThrow(() -> new EntityNotFoundException("Envio no encontrado para notificacion: " + notificacionId));

        return envioMapper.toResponse(envio);
    }

    public List<EnvioResponse> findByEstado(String estado) {
        return envioMapper.toResponseList(envioRepository.findByEstado(estado));
    }

    public List<EnvioResponse> findByEnviadoEn(LocalDate enviadoEn) {
        return envioMapper.toResponseList(envioRepository.findByEnviadoEn(enviadoEn));
    }
    @Transactional
    public EnvioResponse create(EnvioRequest request) {
        if (envioRepository.existsByNotificacionId(request.getNotificacionId())) {
            throw new IllegalArgumentException("Ya existe un envio para la notificacion: " + request.getNotificacionId());
        }

        Notificacion notificacion = notificacionRepository.findById(request.getNotificacionId())
                .orElseThrow(() -> new EntityNotFoundException("Notificacion no encontrada con id: " + request.getNotificacionId()));

        Envio envio = envioMapper.toEntity(request);
        envio.setNotificacion(notificacion);
        envio.setEstado(request.getEstado() != null ? request.getEstado() : "PENDIENTE");
        envio.setIntentos(request.getIntentos() != null ? request.getIntentos() : 0);
        envio.setEnviadoEn("ENVIADO".equals(envio.getEstado()) ? LocalDate.now() : null);

        Envio envioGuardado = envioRepository.save(envio);

        return envioMapper.toResponse(envioGuardado);
    }
    @Transactional
    public EnvioResponse update(Long id, EnvioRequest request) {
        Envio envio = getEnvioById(id);

        if (!envio.getNotificacion().getId().equals(request.getNotificacionId())
                && envioRepository.existsByNotificacionId(request.getNotificacionId())) {
            throw new IllegalArgumentException("Ya existe un envio para la notificacion: " + request.getNotificacionId());
        }

        Notificacion notificacion = notificacionRepository.findById(request.getNotificacionId())
                .orElseThrow(() -> new EntityNotFoundException("Notificacion no encontrada con id: " + request.getNotificacionId()));

        envioMapper.updateEntity(request, envio);
        envio.setNotificacion(notificacion);
        envio.setEstado(request.getEstado() != null ? request.getEstado() : envio.getEstado());
        envio.setIntentos(request.getIntentos() != null ? request.getIntentos() : envio.getIntentos());

        if ("ENVIADO".equals(envio.getEstado()) && envio.getEnviadoEn() == null) {
            envio.setEnviadoEn(LocalDate.now());
        }

        Envio envioActualizado = envioRepository.save(envio);

        return envioMapper.toResponse(envioActualizado);
    }
    @Transactional
    public void deleteById(Long id) {
        Envio envio = getEnvioById(id);
        envioRepository.delete(envio);
    }

    private Envio getEnvioById(Long id) {
        return envioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Envio no encontrado con id: " + id));
    }
}
