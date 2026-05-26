package cl.hilton.notificaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.notificaciones.dto.NotificacionRequest;
import cl.hilton.notificaciones.dto.NotificacionResponse;
import cl.hilton.notificaciones.mapper.NotificacionMapper;
import cl.hilton.notificaciones.model.Notificacion;
import cl.hilton.notificaciones.model.Plantilla;
import cl.hilton.notificaciones.model.ProjHuesped;
import cl.hilton.notificaciones.repository.NotificacionRepository;
import cl.hilton.notificaciones.repository.PlantillaRepository;
import cl.hilton.notificaciones.repository.ProjHuespedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final PlantillaRepository plantillaRepository;
    private final ProjHuespedRepository huespedRepository;
    private final NotificacionMapper notificacionMapper;

    public List<NotificacionResponse> findAll() {
        return notificacionMapper.toResponseList(notificacionRepository.findAll());
    }

    public NotificacionResponse findById(Long id) {
        Notificacion notificacion = getNotificacionById(id);
        return notificacionMapper.toResponse(notificacion);
    }

    public List<NotificacionResponse> findByEventoOrigen(String eventoOrigen) {
        return notificacionMapper.toResponseList(notificacionRepository.findByEventoOrigen(eventoOrigen));
    }

    public NotificacionResponse create(NotificacionRequest request) {
        Plantilla plantilla = plantillaRepository.findByCodigo(request.getCodigoPlantilla())
                .orElseThrow(() -> new EntityNotFoundException("Plantilla no encontrada con codigo: " + request.getCodigoPlantilla()));

        ProjHuesped huesped = huespedRepository.findByEmail(request.getEmailHuesped())
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + request.getEmailHuesped()));

        Notificacion notificacion = notificacionMapper.toEntity(request);
        notificacion.setPlantilla(plantilla);
        notificacion.setHuesped(huesped);
        notificacion.setCreadoEn(LocalDate.now());

        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);

        return notificacionMapper.toResponse(notificacionGuardada);
    }

    public NotificacionResponse update(Long id, NotificacionRequest request) {
        Notificacion notificacion = getNotificacionById(id);

        Plantilla plantilla = plantillaRepository.findByCodigo(request.getCodigoPlantilla())
                .orElseThrow(() -> new EntityNotFoundException("Plantilla no encontrada con codigo: " + request.getCodigoPlantilla()));

        ProjHuesped huesped = huespedRepository.findByEmail(request.getEmailHuesped())
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + request.getEmailHuesped()));

        notificacionMapper.updateEntity(request, notificacion);
        notificacion.setPlantilla(plantilla);
        notificacion.setHuesped(huesped);

        Notificacion notificacionActualizada = notificacionRepository.save(notificacion);

        return notificacionMapper.toResponse(notificacionActualizada);
    }

    public void deleteById(Long id) {
        Notificacion notificacion = getNotificacionById(id);
        notificacionRepository.delete(notificacion);
    }

    private Notificacion getNotificacionById(Long id) {
        return notificacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificacion no encontrada con id: " + id));
    }
}
