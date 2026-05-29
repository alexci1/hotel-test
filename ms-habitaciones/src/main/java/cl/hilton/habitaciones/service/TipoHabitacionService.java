package cl.hilton.habitaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.habitaciones.dto.TipoHabitacionRequest;
import cl.hilton.habitaciones.dto.TipoHabitacionResponse;
import cl.hilton.habitaciones.mapper.TipoHabitacionMapper;
import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.repository.TipoHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoHabitacionService {

    private final TipoHabitacionRepository tipoHabitacionRepository;
    private final TipoHabitacionMapper tipoHabitacionMapper;

    public List<TipoHabitacionResponse> findAll() {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findAll());
    }

    public TipoHabitacionResponse findById(Long id) {
        TipoHabitacion tipoHabitacion = getTipoHabitacionById(id);
        return tipoHabitacionMapper.toResponse(tipoHabitacion);
    }

    public TipoHabitacionResponse findByCodigo(String codigo) {
        TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion no encontrado con codigo: " + codigo));

        return tipoHabitacionMapper.toResponse(tipoHabitacion);
    }

    public List<TipoHabitacionResponse> findByActivo(Boolean activo) {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByActivo(activo));
    }

    public List<TipoHabitacionResponse> findByCapacidadMax(Integer capacidadMax) {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByCapacidadMax(capacidadMax));
    }

    public List<TipoHabitacionResponse> findByCapacidadMinima(Integer capacidadMax) {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByCapacidadMaxGreaterThanEqual(capacidadMax));
    }

    public List<TipoHabitacionResponse> findByDescripcion(String descripcion) {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByDescripcionContainingIgnoreCase(descripcion));
    }

    public TipoHabitacionResponse create(TipoHabitacionRequest request) {
        validarCodigoUnico(request.getCodigo());

        TipoHabitacion tipoHabitacion = tipoHabitacionMapper.toEntity(request);
        tipoHabitacion.setCapacidadMax(request.getCapacidadMax() != null ? request.getCapacidadMax() : 2);
        tipoHabitacion.setActivo(request.getActivo() != null ? request.getActivo() : true);

        TipoHabitacion tipoHabitacionGuardado = tipoHabitacionRepository.save(tipoHabitacion);

        return tipoHabitacionMapper.toResponse(tipoHabitacionGuardado);
    }

    public TipoHabitacionResponse update(Long id, TipoHabitacionRequest request) {
        TipoHabitacion tipoHabitacion = getTipoHabitacionById(id);
        Integer capacidadActual = tipoHabitacion.getCapacidadMax();
        Boolean activoActual = tipoHabitacion.getActivo();

        if (!tipoHabitacion.getCodigo().equalsIgnoreCase(request.getCodigo())) {
            validarCodigoUnico(request.getCodigo());
        }

        tipoHabitacionMapper.updateEntity(request, tipoHabitacion);
        tipoHabitacion.setCapacidadMax(request.getCapacidadMax() != null ? request.getCapacidadMax() : capacidadActual);
        tipoHabitacion.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        TipoHabitacion tipoHabitacionActualizado = tipoHabitacionRepository.save(tipoHabitacion);

        return tipoHabitacionMapper.toResponse(tipoHabitacionActualizado);
    }

    public void deleteById(Long id) {
        TipoHabitacion tipoHabitacion = getTipoHabitacionById(id);
        tipoHabitacionRepository.delete(tipoHabitacion);
    }

    private TipoHabitacion getTipoHabitacionById(Long id) {
        return tipoHabitacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion no encontrado con id: " + id));
    }

    private void validarCodigoUnico(String codigo) {
        if (tipoHabitacionRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un tipo de habitacion con codigo: " + codigo);
        }
    }
}
