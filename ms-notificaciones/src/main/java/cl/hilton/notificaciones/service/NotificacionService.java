package cl.hilton.notificaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.notificaciones.dto.NotificacionRequest;
import cl.hilton.notificaciones.dto.NotificacionResponse;
import cl.hilton.notificaciones.mapper.NotificacionMapper;
import cl.hilton.notificaciones.model.Notificacion;
import cl.hilton.notificaciones.model.Plantilla;
import cl.hilton.notificaciones.model.ProjHuesped;
import cl.hilton.notificaciones.repository.NotificacionRepository;
import cl.hilton.notificaciones.repository.PlantillaRepository;
import cl.hilton.notificaciones.repository.ProjHuespedRepository;
import cl.hilton.common.exception.EntityNotFoundException;
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
        String evento = validarTexto(eventoOrigen, "eventoOrigen");
        return notificacionMapper.toResponseList(notificacionRepository.findByEventoOrigen(evento));
    }

    public List<NotificacionResponse> findByCodigoPlantilla(String codigoPlantilla) {
        String codigo = validarTexto(codigoPlantilla, "codigoPlantilla");
        return notificacionMapper.toResponseList(notificacionRepository.findByPlantillaCodigo(codigo));
    }

    public List<NotificacionResponse> findByEmailHuesped(String emailHuesped) {
        String email = validarTexto(emailHuesped, "emailHuesped");
        return notificacionMapper.toResponseList(notificacionRepository.findByHuespedEmail(email));
    }

    public List<NotificacionResponse> findByCreadoEn(LocalDate creadoEn) {
        LocalDate fecha = validarFecha(creadoEn, "creadoEn");
        return notificacionMapper.toResponseList(notificacionRepository.findByCreadoEn(fecha));
    }

    @Transactional
    public NotificacionResponse create(NotificacionRequest request) {
        String codigoPlantilla = validarTexto(request.getCodigoPlantilla(), "codigoPlantilla");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");

        Plantilla plantilla = getPlantillaByCodigo(codigoPlantilla);
        ProjHuesped huesped = getHuespedByEmail(emailHuesped);

        Notificacion notificacion = notificacionMapper.toEntity(request);
        notificacion.setPlantilla(plantilla);
        notificacion.setHuesped(huesped);
        notificacion.setCreadoEn(LocalDate.now());

        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);

        return notificacionMapper.toResponse(notificacionGuardada);
    }

    @Transactional
    public NotificacionResponse update(Long id, NotificacionRequest request) {
        Long notificacionId = validarId(id);
        String codigoPlantilla = validarTexto(request.getCodigoPlantilla(), "codigoPlantilla");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");

        Notificacion notificacion = getNotificacionById(notificacionId);
        Plantilla plantilla = getPlantillaByCodigo(codigoPlantilla);
        ProjHuesped huesped = getHuespedByEmail(emailHuesped);

        notificacionMapper.updateEntity(request, notificacion);
        notificacion.setPlantilla(plantilla);
        notificacion.setHuesped(huesped);

        Notificacion notificacionActualizada = notificacionRepository.save(notificacion);

        return notificacionMapper.toResponse(notificacionActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long notificacionId = validarId(id);
        getNotificacionById(notificacionId);
        notificacionRepository.deleteById(notificacionId);
    }

    private Notificacion getNotificacionById(Long id) {
        Long notificacionId = validarId(id);

        return notificacionRepository.findById(notificacionId)
                .orElseThrow(() -> new EntityNotFoundException("Notificacion no encontrada con id: " + notificacionId));
    }

    private Plantilla getPlantillaByCodigo(String codigoPlantilla) {
        String codigo = validarTexto(codigoPlantilla, "codigoPlantilla");

        return plantillaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Plantilla no encontrada con codigo: " + codigo));
    }

    private ProjHuesped getHuespedByEmail(String emailHuesped) {
        String email = validarTexto(emailHuesped, "emailHuesped");

        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + email));
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

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
