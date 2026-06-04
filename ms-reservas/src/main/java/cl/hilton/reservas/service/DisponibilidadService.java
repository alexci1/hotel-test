package cl.hilton.reservas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.reservas.dto.DisponibilidadRequest;
import cl.hilton.reservas.dto.DisponibilidadResponse;
import cl.hilton.reservas.mapper.DisponibilidadMapper;
import cl.hilton.reservas.model.Disponibilidad;
import cl.hilton.reservas.model.ProjHabitacion;
import cl.hilton.reservas.repository.DisponibilidadRepository;
import cl.hilton.reservas.repository.ProjHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;
    private final ProjHabitacionRepository habitacionRepository;
    private final DisponibilidadMapper disponibilidadMapper;

    public List<DisponibilidadResponse> findAll() {
        return disponibilidadMapper.toResponseList(disponibilidadRepository.findAll());
    }

    public DisponibilidadResponse findById(Long id) {
        Disponibilidad disponibilidad = getDisponibilidadById(id);
        return disponibilidadMapper.toResponse(disponibilidad);
    }

    public DisponibilidadResponse findByHabitacionAndFecha(String numeroHabitacion, LocalDate fecha) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        LocalDate fechaValida = validarFecha(fecha, "fecha");

        Disponibilidad disponibilidad = disponibilidadRepository.findByHabitacionNumeroHabitacionAndFecha(numero, fechaValida)
                .orElseThrow(() -> new EntityNotFoundException("Disponibilidad no encontrada para habitacion y fecha indicadas"));

        return disponibilidadMapper.toResponse(disponibilidad);
    }

    public List<DisponibilidadResponse> findByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        return disponibilidadMapper.toResponseList(disponibilidadRepository.findByHabitacionNumeroHabitacion(numero));
    }

    public List<DisponibilidadResponse> findByFecha(LocalDate fecha) {
        LocalDate fechaValida = validarFecha(fecha, "fecha");
        return disponibilidadMapper.toResponseList(disponibilidadRepository.findByFecha(fechaValida));
    }

    public List<DisponibilidadResponse> findByRangoFechas(LocalDate desde, LocalDate hasta) {
        LocalDate fechaDesde = validarFecha(desde, "desde");
        LocalDate fechaHasta = validarFecha(hasta, "hasta");
        validarRangoFechas(fechaDesde, fechaHasta);

        return disponibilidadMapper.toResponseList(disponibilidadRepository.findByFechaBetween(fechaDesde, fechaHasta));
    }

    public List<DisponibilidadResponse> findByDisponible(Boolean disponible) {
        Boolean estado = validarBoolean(disponible, "disponible");
        return disponibilidadMapper.toResponseList(disponibilidadRepository.findByDisponible(estado));
    }

    @Transactional
    public DisponibilidadResponse create(DisponibilidadRequest request) {
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");
        LocalDate fecha = validarFecha(request.getFecha(), "fecha");

        if (disponibilidadRepository.existsByHabitacionNumeroHabitacionAndFecha(numeroHabitacion, fecha)) {
            throw new IllegalArgumentException("Ya existe disponibilidad para esa habitacion y fecha");
        }

        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);

        Disponibilidad disponibilidad = disponibilidadMapper.toEntity(request);
        disponibilidad.setHabitacion(habitacion);
        disponibilidad.setDisponible(request.getDisponible() != null ? request.getDisponible() : true);

        Disponibilidad disponibilidadGuardada = disponibilidadRepository.save(disponibilidad);

        return disponibilidadMapper.toResponse(disponibilidadGuardada);
    }

    @Transactional
    public DisponibilidadResponse update(Long id, DisponibilidadRequest request) {
        Long disponibilidadId = validarId(id);
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");
        LocalDate fecha = validarFecha(request.getFecha(), "fecha");

        Disponibilidad disponibilidad = getDisponibilidadById(disponibilidadId);
        Boolean disponibleActual = disponibilidad.getDisponible();

        if (!disponibilidad.getHabitacion().getNumeroHabitacion().equalsIgnoreCase(numeroHabitacion)
                || !disponibilidad.getFecha().equals(fecha)) {
            if (disponibilidadRepository.existsByHabitacionNumeroHabitacionAndFecha(numeroHabitacion, fecha)) {
                throw new IllegalArgumentException("Ya existe disponibilidad para esa habitacion y fecha");
            }
        }

        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);

        disponibilidadMapper.updateEntity(request, disponibilidad);
        disponibilidad.setHabitacion(habitacion);
        disponibilidad.setDisponible(request.getDisponible() != null ? request.getDisponible() : disponibleActual);

        Disponibilidad disponibilidadActualizada = disponibilidadRepository.save(disponibilidad);

        return disponibilidadMapper.toResponse(disponibilidadActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long disponibilidadId = validarId(id);
        getDisponibilidadById(disponibilidadId);
        disponibilidadRepository.deleteById(disponibilidadId);
    }

    private Disponibilidad getDisponibilidadById(Long id) {
        Long disponibilidadId = validarId(id);

        return disponibilidadRepository.findById(disponibilidadId)
                .orElseThrow(() -> new EntityNotFoundException("Disponibilidad no encontrada con id: " + disponibilidadId));
    }

    private ProjHabitacion getHabitacionByNumero(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        return habitacionRepository.findByNumeroHabitacion(numero)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada: " + numero));
    }

    private void validarRangoFechas(LocalDate desde, LocalDate hasta) {
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a la fecha hasta");
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
