package cl.hilton.reservas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

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
        Disponibilidad disponibilidad = disponibilidadRepository.findByHabitacionNumeroHabitacionAndFecha(numeroHabitacion, fecha)
                .orElseThrow(() -> new EntityNotFoundException("Disponibilidad no encontrada para habitacion y fecha indicadas"));

        return disponibilidadMapper.toResponse(disponibilidad);
    }

    public List<DisponibilidadResponse> findByNumeroHabitacion(String numeroHabitacion) {
        return disponibilidadMapper.toResponseList(disponibilidadRepository.findByHabitacionNumeroHabitacion(numeroHabitacion));
    }

    public List<DisponibilidadResponse> findByFecha(LocalDate fecha) {
        return disponibilidadMapper.toResponseList(disponibilidadRepository.findByFecha(fecha));
    }

    public List<DisponibilidadResponse> findByRangoFechas(LocalDate desde, LocalDate hasta) {
        return disponibilidadMapper.toResponseList(disponibilidadRepository.findByFechaBetween(desde, hasta));
    }

    public List<DisponibilidadResponse> findByDisponible(Boolean disponible) {
        return disponibilidadMapper.toResponseList(disponibilidadRepository.findByDisponible(disponible));
    }

    public DisponibilidadResponse create(DisponibilidadRequest request) {
        if (disponibilidadRepository.existsByHabitacionNumeroHabitacionAndFecha(request.getNumeroHabitacion(), request.getFecha())) {
            throw new IllegalArgumentException("Ya existe disponibilidad para esa habitacion y fecha");
        }

        ProjHabitacion habitacion = habitacionRepository.findByNumeroHabitacion(request.getNumeroHabitacion())
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada: " + request.getNumeroHabitacion()));

        Disponibilidad disponibilidad = disponibilidadMapper.toEntity(request);
        disponibilidad.setHabitacion(habitacion);
        disponibilidad.setDisponible(request.getDisponible() != null ? request.getDisponible() : true);

        Disponibilidad disponibilidadGuardada = disponibilidadRepository.save(disponibilidad);

        return disponibilidadMapper.toResponse(disponibilidadGuardada);
    }

    public DisponibilidadResponse update(Long id, DisponibilidadRequest request) {
        Disponibilidad disponibilidad = getDisponibilidadById(id);
        Boolean disponibleActual = disponibilidad.getDisponible();

        if (!disponibilidad.getHabitacion().getNumeroHabitacion().equalsIgnoreCase(request.getNumeroHabitacion())
                || !disponibilidad.getFecha().equals(request.getFecha())) {
            if (disponibilidadRepository.existsByHabitacionNumeroHabitacionAndFecha(request.getNumeroHabitacion(), request.getFecha())) {
                throw new IllegalArgumentException("Ya existe disponibilidad para esa habitacion y fecha");
            }
        }

        ProjHabitacion habitacion = habitacionRepository.findByNumeroHabitacion(request.getNumeroHabitacion())
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada: " + request.getNumeroHabitacion()));

        disponibilidadMapper.updateEntity(request, disponibilidad);
        disponibilidad.setHabitacion(habitacion);
        disponibilidad.setDisponible(request.getDisponible() != null ? request.getDisponible() : disponibleActual);

        Disponibilidad disponibilidadActualizada = disponibilidadRepository.save(disponibilidad);

        return disponibilidadMapper.toResponse(disponibilidadActualizada);
    }

    public void deleteById(Long id) {
        Disponibilidad disponibilidad = getDisponibilidadById(id);
        disponibilidadRepository.delete(disponibilidad);
    }

    private Disponibilidad getDisponibilidadById(Long id) {
        return disponibilidadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Disponibilidad no encontrada con id: " + id));
    }
}
