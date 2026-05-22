package cl.hilton.habitaciones.service;

import cl.hilton.habitaciones.model.Habitacion;
import cl.hilton.habitaciones.repository.HabitacionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    public List<Habitacion> obtenerHabitaciones() {
        return habitacionRepository.findAll();
    }

    public Optional<Habitacion> obtenerPorId(@NonNull Long id) {
        return habitacionRepository.findById(id);
    }

    public Optional<Habitacion> obtenerPorNumero(String numeroHabitacion) {
        return habitacionRepository.findByNumeroHabitacion(numeroHabitacion);
    }

    public Habitacion guardarHabitacion(@NonNull Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    public void eliminarHabitacion(@NonNull Long id) {
        habitacionRepository.deleteById(id);
    }

    public List<Habitacion> obtenerPorPiso(Integer piso) {
        return habitacionRepository.findByPiso(piso);
    }

    public List<Habitacion> obtenerActivas(Boolean activa) {
        return habitacionRepository.findByActiva(activa);
    }
}