package cl.hilton.habitaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.habitaciones.dto.HabitacionRequest;
import cl.hilton.habitaciones.dto.HabitacionResponse;
import cl.hilton.habitaciones.mapper.HabitacionMapper;
import cl.hilton.habitaciones.model.Habitacion;
import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.repository.HabitacionRepository;
import cl.hilton.habitaciones.repository.TipoHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final TipoHabitacionRepository tipoHabitacionRepository;
    private final HabitacionMapper habitacionMapper;

    public List<HabitacionResponse> findAll() {
        return habitacionMapper.toResponseList(habitacionRepository.findAll());
    }

    public HabitacionResponse findById(Long id) {
        Habitacion habitacion = getHabitacionById(id);
        return habitacionMapper.toResponse(habitacion);
    }

    public HabitacionResponse findByNumeroHabitacion(String numeroHabitacion) {
        Habitacion habitacion = habitacionRepository.findByNumeroHabitacion(numeroHabitacion)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada con numero: " + numeroHabitacion));

        return habitacionMapper.toResponse(habitacion);
    }

    public List<HabitacionResponse> findByPiso(Integer piso) {
        return habitacionMapper.toResponseList(habitacionRepository.findByPiso(piso));
    }

    public List<HabitacionResponse> findByActiva(Boolean activa) {
        return habitacionMapper.toResponseList(habitacionRepository.findByActiva(activa));
    }

    public List<HabitacionResponse> findByCodigoTipo(String codigoTipo) {
        return habitacionMapper.toResponseList(habitacionRepository.findByTipoHabitacionCodigo(codigoTipo));
    }

    public List<HabitacionResponse> findByCodigoTipoAndActiva(String codigoTipo, Boolean activa) {
        return habitacionMapper.toResponseList(habitacionRepository.findByTipoHabitacionCodigoAndActiva(codigoTipo, activa));
    }

    public HabitacionResponse create(HabitacionRequest request) {
        validarNumeroHabitacionUnico(request.getNumeroHabitacion());

        TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findByCodigo(request.getCodigoTipo())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion no encontrado con codigo: " + request.getCodigoTipo()));

        Habitacion habitacion = habitacionMapper.toEntity(request);
        habitacion.setTipoHabitacion(tipoHabitacion);
        habitacion.setActiva(request.getActiva() != null ? request.getActiva() : true);

        Habitacion habitacionGuardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionGuardada);
    }

    public HabitacionResponse update(Long id, HabitacionRequest request) {
        Habitacion habitacion = getHabitacionById(id);
        Boolean activaActual = habitacion.getActiva();

        if (!habitacion.getNumeroHabitacion().equalsIgnoreCase(request.getNumeroHabitacion())) {
            validarNumeroHabitacionUnico(request.getNumeroHabitacion());
        }

        TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findByCodigo(request.getCodigoTipo())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion no encontrado con codigo: " + request.getCodigoTipo()));

        habitacionMapper.updateEntity(request, habitacion);
        habitacion.setTipoHabitacion(tipoHabitacion);
        habitacion.setActiva(request.getActiva() != null ? request.getActiva() : activaActual);

        Habitacion habitacionActualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionActualizada);
    }

    public HabitacionResponse cambiarActiva(Long id, Boolean activa) {
        Habitacion habitacion = getHabitacionById(id);
        habitacion.setActiva(activa);

        Habitacion habitacionActualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionActualizada);
    }

    public void deleteById(Long id) {
        Habitacion habitacion = getHabitacionById(id);
        habitacionRepository.delete(habitacion);
    }

    private Habitacion getHabitacionById(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada con id: " + id));
    }

    private void validarNumeroHabitacionUnico(String numeroHabitacion) {
        if (habitacionRepository.existsByNumeroHabitacion(numeroHabitacion)) {
            throw new IllegalArgumentException("Ya existe una habitacion con numero: " + numeroHabitacion);
        }
    }
}
