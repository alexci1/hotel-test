package cl.hilton.habitaciones.service;

import cl.hilton.habitaciones.model.ProjTarifa;
import cl.hilton.habitaciones.repository.ProjTarifaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjTarifaService {

    private final ProjTarifaRepository projTarifaRepository;

    public List<ProjTarifa> obtenerTarifas() {
        return projTarifaRepository.findAll();
    }

    public Optional<ProjTarifa> obtenerPorTipoHabitacion(String tipoHabitacion) {
        return projTarifaRepository.findByTipoHabitacion(tipoHabitacion);
    }

    public ProjTarifa guardarTarifa(@NonNull ProjTarifa projTarifa) {
        return projTarifaRepository.save(projTarifa);
    }

    public void eliminarTarifa(@NonNull String tipoHabitacion) {
        projTarifaRepository.deleteById(tipoHabitacion);
    }
}