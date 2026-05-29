package cl.hilton.inventario.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.inventario.client.HabitacionClient;
import cl.hilton.inventario.dto.HabitacionInventarioResponse;
import cl.hilton.inventario.dto.ProjHabitacionRequest;
import cl.hilton.inventario.dto.ProjHabitacionResponse;
import cl.hilton.inventario.mapper.ProjHabitacionMapper;
import cl.hilton.inventario.model.ProjHabitacion;
import cl.hilton.inventario.repository.ProjHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjHabitacionService {

    private final ProjHabitacionRepository habitacionRepository;
    private final ProjHabitacionMapper habitacionMapper;
    private final HabitacionClient habitacionClient;

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

    public List<ProjHabitacionResponse> findByActualizadoEn(LocalDate actualizadoEn) {
        return habitacionMapper.toResponseList(habitacionRepository.findByActualizadoEn(actualizadoEn));
    }

    public ProjHabitacionResponse create(ProjHabitacionRequest request) {
        validarNumeroUnico(request.getNumeroHabitacion());

        ProjHabitacion habitacion = habitacionMapper.toEntity(request);
        habitacion.setActualizadoEn(LocalDate.now());

        ProjHabitacion habitacionGuardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionGuardada);
    }

    public ProjHabitacionResponse update(String numeroHabitacion, ProjHabitacionRequest request) {
        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);

        habitacionMapper.updateEntity(request, habitacion);
        habitacion.setActualizadoEn(LocalDate.now());

        ProjHabitacion habitacionActualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionActualizada);
    }

    public ProjHabitacionResponse sincronizarPorNumeroHabitacion(String numeroHabitacion) {
        HabitacionInventarioResponse externa = habitacionClient.buscarPorNumeroHabitacion(numeroHabitacion);
        ProjHabitacion habitacion = habitacionRepository.findByNumeroHabitacion(externa.getNumeroHabitacion())
                .orElseGet(ProjHabitacion::new);

        habitacion.setNumeroHabitacion(externa.getNumeroHabitacion());
        habitacion.setTipo(externa.getCodigoTipo());
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

    private void validarNumeroUnico(String numeroHabitacion) {
        if (habitacionRepository.existsByNumeroHabitacion(numeroHabitacion)) {
            throw new IllegalArgumentException("Ya existe una habitacion proyectada con numero: " + numeroHabitacion);
        }
    }
}
