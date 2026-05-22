package cl.hilton.housekeeping.service;

import cl.hilton.housekeeping.dto.ProjHabitacionRequest;
import cl.hilton.housekeeping.dto.ProjHabitacionResponse;
import cl.hilton.housekeeping.mapper.ProjHabitacionMapper;
import cl.hilton.housekeeping.model.ProjHabitacion;
import cl.hilton.housekeeping.repository.ProjHabitacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjHabitacionService {

    private final ProjHabitacionRepository habitacionRepository;
    private final ProjHabitacionMapper habitacionMapper;

    public ProjHabitacionService(ProjHabitacionRepository habitacionRepository, ProjHabitacionMapper habitacionMapper) {
        this.habitacionRepository = habitacionRepository;
        this.habitacionMapper = habitacionMapper;
    }

    public List<ProjHabitacionResponse> listar() {
        return habitacionRepository.findAll().stream()
                .map(habitacionMapper::toResponse)
                .toList();
    }

    public ProjHabitacionResponse buscarPorNumero(String numeroHabitacion) {
        return habitacionMapper.toResponse(obtenerHabitacion(numeroHabitacion));
    }

    public List<ProjHabitacionResponse> buscarPorTipo(String tipo) {
        return habitacionRepository.findByTipo(tipo).stream()
                .map(habitacionMapper::toResponse)
                .toList();
    }

    public List<ProjHabitacionResponse> buscarPorPiso(Long piso) {
        return habitacionRepository.findByPiso(piso).stream()
                .map(habitacionMapper::toResponse)
                .toList();
    }

    public ProjHabitacionResponse crear(ProjHabitacionRequest request) {
        if (habitacionRepository.existsById(request.getNumeroHabitacion())) {
            throw new RuntimeException("Ya existe una habitación con ese número");
        }

        ProjHabitacion habitacion = habitacionMapper.toEntity(request);

        return habitacionMapper.toResponse(habitacionRepository.save(habitacion));
    }

    public ProjHabitacionResponse actualizar(String numeroHabitacion, ProjHabitacionRequest request) {
        ProjHabitacion habitacion = obtenerHabitacion(numeroHabitacion);

        habitacionMapper.updateEntity(habitacion, request);

        return habitacionMapper.toResponse(habitacionRepository.save(habitacion));
    }

    public void eliminar(String numeroHabitacion) {
        ProjHabitacion habitacion = obtenerHabitacion(numeroHabitacion);
        habitacionRepository.delete(habitacion);
    }

    private ProjHabitacion obtenerHabitacion(String numeroHabitacion) {
        return habitacionRepository.findById(numeroHabitacion)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    }
}