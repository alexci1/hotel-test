package cl.hilton.habitaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.habitaciones.dto.EstadoHabitacionRequest;
import cl.hilton.habitaciones.dto.EstadoHabitacionResponse;
import cl.hilton.habitaciones.mapper.EstadoHabitacionMapper;
import cl.hilton.habitaciones.model.EstadoHabitacion;
import cl.hilton.habitaciones.model.Habitacion;
import cl.hilton.habitaciones.repository.EstadoHabitacionRepository;
import cl.hilton.habitaciones.repository.HabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoHabitacionService {

    private final EstadoHabitacionRepository estadoHabitacionRepository;
    private final HabitacionRepository habitacionRepository;
    private final EstadoHabitacionMapper estadoHabitacionMapper;

    public List<EstadoHabitacionResponse> findAll() {
        return estadoHabitacionMapper.toResponseList(estadoHabitacionRepository.findAll());
    }

    public EstadoHabitacionResponse findById(Long id) {
        EstadoHabitacion estadoHabitacion = getEstadoHabitacionById(id);
        return estadoHabitacionMapper.toResponse(estadoHabitacion);
    }

    public EstadoHabitacionResponse findByNumeroHabitacion(String numeroHabitacion) {
        EstadoHabitacion estadoHabitacion = estadoHabitacionRepository.findByHabitacionNumeroHabitacion(numeroHabitacion)
                .orElseThrow(() -> new EntityNotFoundException("Estado no encontrado para habitacion: " + numeroHabitacion));

        return estadoHabitacionMapper.toResponse(estadoHabitacion);
    }

    public List<EstadoHabitacionResponse> findByEstado(String estado) {
        return estadoHabitacionMapper.toResponseList(estadoHabitacionRepository.findByEstado(estado));
    }

    public List<EstadoHabitacionResponse> findByActualizadoEn(LocalDate actualizadoEn) {
        return estadoHabitacionMapper.toResponseList(estadoHabitacionRepository.findByActualizadoEn(actualizadoEn));
    }

    public EstadoHabitacionResponse create(EstadoHabitacionRequest request) {
        if (estadoHabitacionRepository.existsByHabitacionNumeroHabitacion(request.getNumeroHabitacion())) {
            throw new IllegalArgumentException("Ya existe estado para la habitacion: " + request.getNumeroHabitacion());
        }

        Habitacion habitacion = habitacionRepository.findByNumeroHabitacion(request.getNumeroHabitacion())
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada con numero: " + request.getNumeroHabitacion()));

        EstadoHabitacion estadoHabitacion = estadoHabitacionMapper.toEntity(request);
        estadoHabitacion.setHabitacion(habitacion);
        estadoHabitacion.setEstado(request.getEstado() != null ? request.getEstado() : "LIMPIA");
        estadoHabitacion.setActualizadoEn(LocalDate.now());

        EstadoHabitacion estadoGuardado = estadoHabitacionRepository.save(estadoHabitacion);

        return estadoHabitacionMapper.toResponse(estadoGuardado);
    }

    public EstadoHabitacionResponse update(Long id, EstadoHabitacionRequest request) {
        EstadoHabitacion estadoHabitacion = getEstadoHabitacionById(id);
        String estadoActual = estadoHabitacion.getEstado();

        if (request.getNumeroHabitacion() != null
                && !estadoHabitacion.getHabitacion().getNumeroHabitacion().equalsIgnoreCase(request.getNumeroHabitacion())) {
            if (estadoHabitacionRepository.existsByHabitacionNumeroHabitacion(request.getNumeroHabitacion())) {
                throw new IllegalArgumentException("Ya existe estado para la habitacion: " + request.getNumeroHabitacion());
            }

            Habitacion habitacion = habitacionRepository.findByNumeroHabitacion(request.getNumeroHabitacion())
                    .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada con numero: " + request.getNumeroHabitacion()));

            estadoHabitacion.setHabitacion(habitacion);
        }

        estadoHabitacionMapper.updateEntity(request, estadoHabitacion);
        estadoHabitacion.setEstado(request.getEstado() != null ? request.getEstado() : estadoActual);
        estadoHabitacion.setActualizadoEn(LocalDate.now());

        EstadoHabitacion estadoActualizado = estadoHabitacionRepository.save(estadoHabitacion);

        return estadoHabitacionMapper.toResponse(estadoActualizado);
    }

    public EstadoHabitacionResponse cambiarEstado(String numeroHabitacion, String estado) {
        EstadoHabitacion estadoHabitacion = estadoHabitacionRepository.findByHabitacionNumeroHabitacion(numeroHabitacion)
                .orElseThrow(() -> new EntityNotFoundException("Estado no encontrado para habitacion: " + numeroHabitacion));

        estadoHabitacion.setEstado(estado);
        estadoHabitacion.setActualizadoEn(LocalDate.now());

        EstadoHabitacion estadoActualizado = estadoHabitacionRepository.save(estadoHabitacion);

        return estadoHabitacionMapper.toResponse(estadoActualizado);
    }

    public void deleteById(Long id) {
        EstadoHabitacion estadoHabitacion = getEstadoHabitacionById(id);
        estadoHabitacionRepository.delete(estadoHabitacion);
    }

    private EstadoHabitacion getEstadoHabitacionById(Long id) {
        return estadoHabitacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estado de habitacion no encontrado con id: " + id));
    }
}
