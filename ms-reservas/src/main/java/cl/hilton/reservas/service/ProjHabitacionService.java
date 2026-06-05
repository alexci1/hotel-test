package cl.hilton.reservas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.reservas.client.HabitacionLookupClient;
import cl.hilton.reservas.dto.ProjHabitacionRequest;
import cl.hilton.reservas.dto.ProjHabitacionResponse;
import cl.hilton.reservas.mapper.ProjHabitacionMapper;
import cl.hilton.reservas.model.ProjHabitacion;
import cl.hilton.reservas.repository.ProjHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjHabitacionService {

    private final ProjHabitacionRepository habitacionRepository;
    private final ProjHabitacionMapper habitacionMapper;
    private final HabitacionLookupClient habitacionClient;

    public List<ProjHabitacionResponse> findAll() {
        return habitacionMapper.toResponseList(habitacionRepository.findAll());
    }

    public ProjHabitacionResponse findByNumeroHabitacion(String numeroHabitacion) {
        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);
        return habitacionMapper.toResponse(habitacion);
    }

    public List<ProjHabitacionResponse> findByTipo(String tipo) {
        return habitacionMapper.toResponseList(habitacionRepository.findByTipo(tipo));
    }

    public List<ProjHabitacionResponse> findByActiva(Boolean activa) {
        return habitacionMapper.toResponseList(habitacionRepository.findByActiva(activa));
    }

    public ProjHabitacionResponse create(ProjHabitacionRequest request) {
        validarNumeroHabitacionUnico(request.getNumeroHabitacion());

        ProjHabitacion habitacion = habitacionMapper.toEntity(request);
        habitacion.setActiva(request.getActiva() != null ? request.getActiva() : true);
        habitacion.setActualizadoEn(LocalDate.now());

        ProjHabitacion habitacionGuardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionGuardada);
    }

    public ProjHabitacionResponse update(String numeroHabitacion, ProjHabitacionRequest request) {
        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);
        Boolean activaActual = habitacion.getActiva();

        habitacionMapper.updateEntity(request, habitacion);
        habitacion.setActiva(request.getActiva() != null ? request.getActiva() : activaActual);
        habitacion.setActualizadoEn(LocalDate.now());

        ProjHabitacion habitacionActualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionActualizada);
    }

    public ProjHabitacionResponse sincronizarPorNumeroHabitacion(String numeroHabitacion) {
        ProjHabitacionResponse externa = habitacionClient.buscarPorNumeroHabitacion(numeroHabitacion);
        ProjHabitacion habitacion = habitacionRepository.findByNumeroHabitacion(externa.getNumeroHabitacion())
                .orElseGet(ProjHabitacion::new);

        habitacion.setNumeroHabitacion(externa.getNumeroHabitacion());
        habitacion.setTipo(externa.getTipo());
        habitacion.setActiva(externa.getActiva());
        habitacion.setActualizadoEn(LocalDate.now());

        ProjHabitacion habitacionGuardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionGuardada);
    }

    public void deleteByNumeroHabitacion(String numeroHabitacion) {
        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);
        habitacionRepository.delete(habitacion);
    }

    private ProjHabitacion getHabitacionByNumero(String numeroHabitacion) {
        return habitacionRepository.findByNumeroHabitacion(numeroHabitacion)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada con numero: " + numeroHabitacion));
    }

    private void validarNumeroHabitacionUnico(String numeroHabitacion) {
        if (habitacionRepository.existsByNumeroHabitacion(numeroHabitacion)) {
            throw new IllegalArgumentException("Ya existe una habitacion proyectada con numero: " + numeroHabitacion);
        }
    }
}
