package cl.hilton.habitaciones.service;

import cl.hilton.habitaciones.model.EstadoHabitacion;
import cl.hilton.habitaciones.repository.EstadoHabitacionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadoHabitacionService {

    private final EstadoHabitacionRepository estadoHabitacionRepository;

    public List<EstadoHabitacion> obtenerEstados() {
        return estadoHabitacionRepository.findAll();
    }

    public Optional<EstadoHabitacion> obtenerPorId(@NonNull Long id) {
        return estadoHabitacionRepository.findById(id);
    }

    public Optional<EstadoHabitacion> obtenerPorNumeroHabitacion(String numeroHabitacion) {
        return estadoHabitacionRepository.findByHabitacionNumeroHabitacion(numeroHabitacion);
    }

    public EstadoHabitacion guardarEstado(@NonNull EstadoHabitacion estadoHabitacion) {
        return estadoHabitacionRepository.save(estadoHabitacion);
    }

    public void eliminarEstado(@NonNull Long id) {
        estadoHabitacionRepository.deleteById(id);
    }

    public List<EstadoHabitacion> obtenerPorEstado(String estado) {
        return estadoHabitacionRepository.findByEstado(estado);
    }
}