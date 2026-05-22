package cl.hilton.reservas.service;

import cl.hilton.reservas.model.Disponibilidad;
import cl.hilton.reservas.repository.DisponibilidadRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;

    public List<Disponibilidad> obtenerDisponibilidades() {
        return disponibilidadRepository.findAll();
    }

    public Optional<Disponibilidad> obtenerPorId(@NonNull Long id) {
        return disponibilidadRepository.findById(id);
    }

    public Disponibilidad guardarDisponibilidad(@NonNull Disponibilidad disponibilidad) {
        return disponibilidadRepository.save(disponibilidad);
    }

    public void eliminarDisponibilidad(@NonNull Long id) {
        disponibilidadRepository.deleteById(id);
    }

    public List<Disponibilidad> obtenerPorFecha(LocalDate fecha) {
        return disponibilidadRepository.findByFecha(fecha);
    }

    public List<Disponibilidad> obtenerPorDisponible(Boolean disponible) {
        return disponibilidadRepository.findByDisponible(disponible);
    }
}