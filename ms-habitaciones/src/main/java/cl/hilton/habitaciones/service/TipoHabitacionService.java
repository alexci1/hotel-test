package cl.hilton.habitaciones.service;

import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.repository.TipoHabitacionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoHabitacionService {

    private final TipoHabitacionRepository tipoHabitacionRepository;

    public List<TipoHabitacion> obtenerTiposHabitacion() {
        return tipoHabitacionRepository.findAll();
    }

    public Optional<TipoHabitacion> obtenerPorId(@NonNull Long id) {
        return tipoHabitacionRepository.findById(id);
    }

    public Optional<TipoHabitacion> obtenerPorCodigo(String codigo) {
        return tipoHabitacionRepository.findByCodigo(codigo);
    }

    public TipoHabitacion guardarTipoHabitacion(@NonNull TipoHabitacion tipoHabitacion) {
        return tipoHabitacionRepository.save(tipoHabitacion);
    }

    public void eliminarTipoHabitacion(@NonNull Long id) {
        tipoHabitacionRepository.deleteById(id);
    }

    public List<TipoHabitacion> obtenerActivos(Boolean activo) {
        return tipoHabitacionRepository.findByActivo(activo);
    }
}