package cl.hilton.habitaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.habitaciones.dto.TipoHabitacionRequest;
import cl.hilton.habitaciones.dto.TipoHabitacionResponse;
import cl.hilton.habitaciones.mapper.TipoHabitacionMapper;
import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.repository.TipoHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
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
        String codigoValido = validarTexto(codigo, "codigo");

        TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findByCodigo(codigoValido)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion no encontrado con codigo: " + codigoValido));

        return tipoHabitacionMapper.toResponse(tipoHabitacion);
    }

    public List<TipoHabitacionResponse> findByActivo(Boolean activo) {
        Boolean estado = validarBoolean(activo, "activo");
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByActivo(estado));
    }

    public List<TipoHabitacionResponse> findByCapacidadMax(Integer capacidadMax) {
        Integer capacidad = validarInteger(capacidadMax, "capacidadMax");
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByCapacidadMax(capacidad));
    }

    public List<TipoHabitacionResponse> findByCapacidadMinima(Integer capacidadMax) {
        Integer capacidad = validarInteger(capacidadMax, "capacidadMax");
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByCapacidadMaxGreaterThanEqual(capacidad));
    }

    public List<TipoHabitacionResponse> findByDescripcion(String descripcion) {
        String texto = validarTexto(descripcion, "descripcion");
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByDescripcionContainingIgnoreCase(texto));
    }

    @Transactional
    public TipoHabitacionResponse create(TipoHabitacionRequest request) {
        String codigo = validarTexto(request.getCodigo(), "codigo");
        validarCodigoUnico(codigo);

        TipoHabitacion tipoHabitacion = tipoHabitacionMapper.toEntity(request);
        tipoHabitacion.setCapacidadMax(request.getCapacidadMax() != null ? request.getCapacidadMax() : 2);
        tipoHabitacion.setActivo(request.getActivo() != null ? request.getActivo() : true);

        TipoHabitacion tipoHabitacionGuardado = tipoHabitacionRepository.save(tipoHabitacion);

        return tipoHabitacionMapper.toResponse(tipoHabitacionGuardado);
    }

    @Transactional
    public TipoHabitacionResponse update(Long id, TipoHabitacionRequest request) {
        Long tipoId = validarId(id);
        String codigo = validarTexto(request.getCodigo(), "codigo");

        TipoHabitacion tipoHabitacion = getTipoHabitacionById(tipoId);
        Integer capacidadActual = tipoHabitacion.getCapacidadMax();
        Boolean activoActual = tipoHabitacion.getActivo();

        if (!tipoHabitacion.getCodigo().equalsIgnoreCase(codigo)) {
            validarCodigoUnico(codigo);
        }

        tipoHabitacionMapper.updateEntity(request, tipoHabitacion);
        tipoHabitacion.setCapacidadMax(request.getCapacidadMax() != null ? request.getCapacidadMax() : capacidadActual);
        tipoHabitacion.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        TipoHabitacion tipoHabitacionActualizado = tipoHabitacionRepository.save(tipoHabitacion);

        return tipoHabitacionMapper.toResponse(tipoHabitacionActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long tipoId = validarId(id);
        getTipoHabitacionById(tipoId);
        tipoHabitacionRepository.deleteById(tipoId);
    }

    private TipoHabitacion getTipoHabitacionById(Long id) {
        Long tipoId = validarId(id);

        return tipoHabitacionRepository.findById(tipoId)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion no encontrado con id: " + tipoId));
    }

    private void validarCodigoUnico(String codigo) {
        if (tipoHabitacionRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un tipo de habitacion con codigo: " + codigo);
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private Integer validarInteger(Integer valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
    }

    private Boolean validarBoolean(Boolean valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
