package cl.hilton.inventario.service;

import cl.hilton.inventario.dto.ProjHabitacionRequest;
import cl.hilton.inventario.dto.ProjHabitacionResponse;
import cl.hilton.inventario.model.ProjHabitacion;
import cl.hilton.inventario.repository.ProjHabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjHabitacionService {

    private final ProjHabitacionRepository habitacionRepository;

    public List<ProjHabitacionResponse> listar() {
        return habitacionRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ProjHabitacionResponse buscarPorNumeroHabitacion(String numeroHabitacion) {
        return toResponse(obtenerHabitacion(numeroHabitacion));
    }

    public List<ProjHabitacionResponse> buscarPorTipo(String tipo) {
        return habitacionRepository.findByTipo(tipo).stream().map(this::toResponse).toList();
    }

    public ProjHabitacionResponse crear(ProjHabitacionRequest request) {
        if (habitacionRepository.existsByNumeroHabitacion(request.getNumeroHabitacion())) {
            throw new RuntimeException("Ya existe una habitación con ese número");
        }

        ProjHabitacion habitacion = ProjHabitacion.builder()
                .numeroHabitacion(request.getNumeroHabitacion())
                .tipo(request.getTipo())
                .actualizadoEn(request.getActualizadoEn() != null ? request.getActualizadoEn() : OffsetDateTime.now())
                .build();

        return toResponse(habitacionRepository.save(habitacion));
    }

    public ProjHabitacionResponse actualizar(String numeroHabitacion, ProjHabitacionRequest request) {
        ProjHabitacion habitacion = obtenerHabitacion(numeroHabitacion);

        habitacion.setTipo(request.getTipo());
        habitacion.setActualizadoEn(request.getActualizadoEn() != null ? request.getActualizadoEn() : OffsetDateTime.now());

        return toResponse(habitacionRepository.save(habitacion));
    }

    public void eliminar(String numeroHabitacion) {
        ProjHabitacion habitacion = obtenerHabitacion(numeroHabitacion);
        habitacionRepository.delete(habitacion);
    }

    private ProjHabitacion obtenerHabitacion(String numeroHabitacion) {
        return habitacionRepository.findById(numeroHabitacion)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    }

    private ProjHabitacionResponse toResponse(ProjHabitacion habitacion) {
        return ProjHabitacionResponse.builder()
                .numeroHabitacion(habitacion.getNumeroHabitacion())
                .tipo(habitacion.getTipo())
                .actualizadoEn(habitacion.getActualizadoEn())
                .build();
    }
}


