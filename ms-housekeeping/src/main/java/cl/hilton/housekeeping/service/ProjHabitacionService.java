package cl.hilton.housekeeping.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.hilton.housekeeping.dto.ProjHabitacionRequest;
import cl.hilton.housekeeping.dto.ProjHabitacionResponse;
import cl.hilton.housekeeping.mapper.ProjHabitacionMapper;
import cl.hilton.housekeeping.model.ProjHabitacion;
import cl.hilton.housekeeping.repository.ProjHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProjHabitacionService {

    private final ProjHabitacionRepository habitacionRepository;
    private final ProjHabitacionMapper habitacionMapper;

    public List<ProjHabitacionResponse> findAll() {
        return habitacionMapper.toResponseList(habitacionRepository.findAll());
    }

    public ProjHabitacionResponse findByNumeroHabitacion(String numeroHabitacion) {
        return habitacionMapper.toResponse(getHabitacion(numeroHabitacion));
    }

    public List<ProjHabitacionResponse> findByTipo(String tipo) {
        return habitacionMapper.toResponseList(habitacionRepository.findByTipo(tipo));
    }

    public List<ProjHabitacionResponse> findByPiso(Integer piso) {
        return habitacionMapper.toResponseList(habitacionRepository.findByPiso(piso));
    }

    public ProjHabitacionResponse create(ProjHabitacionRequest request) {
        if (habitacionRepository.existsById(request.getNumeroHabitacion())) {
            throw new IllegalArgumentException("Ya existe una habitacion con numero: " + request.getNumeroHabitacion());
        }

        ProjHabitacion habitacion = habitacionMapper.toEntity(request);
        ProjHabitacion saved = habitacionRepository.save(Objects.requireNonNull(habitacion));
        return habitacionMapper.toResponse(saved);
    }

    public ProjHabitacionResponse update(String numeroHabitacion, ProjHabitacionRequest request) {
        ProjHabitacion habitacion = getHabitacion(numeroHabitacion);
        habitacionMapper.updateEntity(habitacion, request);

        ProjHabitacion saved = habitacionRepository.save(Objects.requireNonNull(habitacion));
        return habitacionMapper.toResponse(saved);
    }

    public void deleteByNumeroHabitacion(String numeroHabitacion) {
        ProjHabitacion habitacion = getHabitacion(numeroHabitacion);
        habitacionRepository.delete(Objects.requireNonNull(habitacion));
    }

    private ProjHabitacion getHabitacion(String numeroHabitacion) {
        return habitacionRepository.findById(numeroHabitacion)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada: " + numeroHabitacion));
    }
}