package cl.hilton.tarifas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.tarifas.client.TipoHabitacionClient;
import cl.hilton.tarifas.dto.ProjTipoHabitacionRequest;
import cl.hilton.tarifas.dto.ProjTipoHabitacionResponse;
import cl.hilton.tarifas.mapper.ProjTipoHabitacionMapper;
import cl.hilton.tarifas.model.ProjTipoHabitacion;
import cl.hilton.tarifas.repository.ProjTipoHabitacionRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProjTipoHabitacionService {

    private final ProjTipoHabitacionRepository tipoHabitacionRepository;
    private final ProjTipoHabitacionMapper tipoHabitacionMapper;
    private final TipoHabitacionClient tipoHabitacionClient;

    public List<ProjTipoHabitacionResponse> findAll() {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findAll());
    }

    public ProjTipoHabitacionResponse findByCodigo(String codigo) {
        ProjTipoHabitacion tipoHabitacion = getTipoHabitacionByCodigo(codigo);
        return tipoHabitacionMapper.toResponse(tipoHabitacion);
    }

    public List<ProjTipoHabitacionResponse> findByCapacidadMax(Integer capacidadMax) {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByCapacidadMax(capacidadMax));
    }

    public List<ProjTipoHabitacionResponse> findByCapacidadMinima(Integer capacidadMax) {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByCapacidadMaxGreaterThanEqual(capacidadMax));
    }

    public List<ProjTipoHabitacionResponse> findByDescripcion(String descripcion) {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByDescripcionContainingIgnoreCase(descripcion));
    }

    public List<ProjTipoHabitacionResponse> findByActualizadoEn(LocalDate actualizadoEn) {
        return tipoHabitacionMapper.toResponseList(tipoHabitacionRepository.findByActualizadoEn(actualizadoEn));
    }

    public ProjTipoHabitacionResponse create(ProjTipoHabitacionRequest request) {
        validarCodigoUnico(request.getCodigo());

        ProjTipoHabitacion tipoHabitacion = tipoHabitacionMapper.toEntity(request);
        tipoHabitacion.setActualizadoEn(LocalDate.now());

        ProjTipoHabitacion tipoHabitacionGuardado = tipoHabitacionRepository.save(tipoHabitacion);

        return tipoHabitacionMapper.toResponse(tipoHabitacionGuardado);
    }

    public ProjTipoHabitacionResponse update(String codigo, ProjTipoHabitacionRequest request) {
        ProjTipoHabitacion tipoHabitacion = getTipoHabitacionByCodigo(codigo);

        tipoHabitacionMapper.updateEntity(request, tipoHabitacion);
        tipoHabitacion.setActualizadoEn(LocalDate.now());

        ProjTipoHabitacion tipoHabitacionActualizado = tipoHabitacionRepository.save(tipoHabitacion);

        return tipoHabitacionMapper.toResponse(tipoHabitacionActualizado);
    }

    public ProjTipoHabitacionResponse sincronizarPorCodigo(String codigo) {
        ProjTipoHabitacionResponse externo = tipoHabitacionClient.buscarPorCodigo(codigo);
        ProjTipoHabitacion tipoHabitacion = tipoHabitacionRepository.findByCodigo(externo.getCodigo())
                .orElseGet(ProjTipoHabitacion::new);

        tipoHabitacion.setCodigo(externo.getCodigo());
        tipoHabitacion.setDescripcion(externo.getDescripcion());
        tipoHabitacion.setCapacidadMax(externo.getCapacidadMax());
        tipoHabitacion.setActualizadoEn(LocalDate.now());

        ProjTipoHabitacion tipoHabitacionGuardado = tipoHabitacionRepository.save(tipoHabitacion);

        return tipoHabitacionMapper.toResponse(tipoHabitacionGuardado);
    }

    public void deleteByCodigo(String codigo) {
        ProjTipoHabitacion tipoHabitacion = getTipoHabitacionByCodigo(codigo);
        tipoHabitacionRepository.delete(tipoHabitacion);
    }

    private ProjTipoHabitacion getTipoHabitacionByCodigo(String codigo) {
        return tipoHabitacionRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion proyectado no encontrado con codigo: " + codigo));
    }

    private void validarCodigoUnico(String codigo) {
        if (tipoHabitacionRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un tipo de habitacion proyectado con codigo: " + codigo);
        }
    }
}
