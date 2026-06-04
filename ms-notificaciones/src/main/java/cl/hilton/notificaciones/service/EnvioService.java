package cl.hilton.notificaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.notificaciones.dto.EnvioRequest;
import cl.hilton.notificaciones.dto.EnvioResponse;
import cl.hilton.notificaciones.mapper.EnvioMapper;
import cl.hilton.notificaciones.model.Envio;
import cl.hilton.notificaciones.model.Notificacion;
import cl.hilton.notificaciones.repository.EnvioRepository;
import cl.hilton.notificaciones.repository.NotificacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
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
        Long idNotificacion = validarId(notificacionId);

        Envio envio = envioRepository.findByNotificacionId(idNotificacion)
                .orElseThrow(() -> new EntityNotFoundException("Envio no encontrado para notificacion: " + idNotificacion));

        return envioMapper.toResponse(envio);
    }

    public List<EnvioResponse> findByEstado(String estado) {
        String estadoValido = validarTexto(estado, "estado");
        return envioMapper.toResponseList(envioRepository.findByEstado(estadoValido));
    }

    public List<EnvioResponse> findByEnviadoEn(LocalDate enviadoEn) {
        LocalDate fecha = validarFecha(enviadoEn, "enviadoEn");
        return envioMapper.toResponseList(envioRepository.findByEnviadoEn(fecha));
    }

    @Transactional
    public EnvioResponse create(EnvioRequest request) {
        Long notificacionId = validarId(request.getNotificacionId());

        if (envioRepository.existsByNotificacionId(notificacionId)) {
            throw new IllegalArgumentException("Ya existe un envio para la notificacion: " + notificacionId);
        }

        Notificacion notificacion = getNotificacionById(notificacionId);

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
        Long envioId = validarId(id);
        Long notificacionId = validarId(request.getNotificacionId());

        Envio envio = getEnvioById(envioId);
        String estadoActual = envio.getEstado();
        Integer intentosActual = envio.getIntentos();

        if (!envio.getNotificacion().getId().equals(notificacionId)
                && envioRepository.existsByNotificacionId(notificacionId)) {
            throw new IllegalArgumentException("Ya existe un envio para la notificacion: " + notificacionId);
        }

        Notificacion notificacion = getNotificacionById(notificacionId);

        envioMapper.updateEntity(request, envio);
        envio.setNotificacion(notificacion);
        envio.setEstado(request.getEstado() != null ? request.getEstado() : estadoActual);
        envio.setIntentos(request.getIntentos() != null ? request.getIntentos() : intentosActual);

        if ("ENVIADO".equals(envio.getEstado()) && envio.getEnviadoEn() == null) {
            envio.setEnviadoEn(LocalDate.now());
        }

        Envio envioActualizado = envioRepository.save(envio);

        return envioMapper.toResponse(envioActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long envioId = validarId(id);
        getEnvioById(envioId);
        envioRepository.deleteById(envioId);
    }

    private Envio getEnvioById(Long id) {
        Long envioId = validarId(id);

        return envioRepository.findById(envioId)
                .orElseThrow(() -> new EntityNotFoundException("Envio no encontrado con id: " + envioId));
    }

    private Notificacion getNotificacionById(Long id) {
        Long notificacionId = validarId(id);

        return notificacionRepository.findById(notificacionId)
                .orElseThrow(() -> new EntityNotFoundException("Notificacion no encontrada con id: " + notificacionId));
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    @SuppressWarnings("unused")
    private Integer validarInteger(Integer valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
    }

    private LocalDate validarFecha(LocalDate valor, String campo) {
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
