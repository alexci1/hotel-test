package cl.hilton.tarifas.service;

import cl.hilton.tarifas.model.ProjTipoHabitacion;
import cl.hilton.tarifas.repository.ProjTipoHabitacionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjTipoHabitacionService {

    private final ProjTipoHabitacionRepository projTipoHabitacionRepository;

    public List<ProjTipoHabitacion> obtenerTiposHabitacion() {
        return projTipoHabitacionRepository.findAll();
    }

    public Optional<ProjTipoHabitacion> obtenerPorCodigo(String codigo) {
        return projTipoHabitacionRepository.findByCodigo(codigo);
    }

    public ProjTipoHabitacion guardarTipoHabitacion(@NonNull ProjTipoHabitacion tipoHabitacion) {
        return projTipoHabitacionRepository.save(tipoHabitacion);
    }

    public void eliminarTipoHabitacion(@NonNull String codigo) {
        projTipoHabitacionRepository.deleteById(codigo);
    }

    public List<ProjTipoHabitacion> obtenerPorCapacidad(Short capacidadMax) {
        return projTipoHabitacionRepository.findByCapacidadMax(capacidadMax);
    }

    public List<ProjTipoHabitacion> buscarPorDescripcion(String descripcion) {
        return projTipoHabitacionRepository.findByDescripcionContainingIgnoreCase(descripcion);
    }
}