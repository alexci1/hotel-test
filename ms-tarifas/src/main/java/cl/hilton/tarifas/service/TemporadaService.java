package cl.hilton.tarifas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.tarifas.dto.TemporadaRequest;
import cl.hilton.tarifas.dto.TemporadaResponse;
import cl.hilton.tarifas.mapper.TemporadaMapper;
import cl.hilton.tarifas.model.Temporada;
import cl.hilton.tarifas.repository.TemporadaRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemporadaService {

    private final TemporadaRepository temporadaRepository;
    private final TemporadaMapper temporadaMapper;

    public List<TemporadaResponse> findAll() {
        return temporadaMapper.toResponseList(temporadaRepository.findAll());
    }

    public TemporadaResponse findById(Long id) {
        Temporada temporada = getTemporadaById(id);
        return temporadaMapper.toResponse(temporada);
    }

    public TemporadaResponse findByCodigo(String codigo) {
        Temporada temporada = temporadaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Temporada no encontrada con codigo: " + codigo));

        return temporadaMapper.toResponse(temporada);
    }

    public List<TemporadaResponse> findByNombre(String nombre) {
        return temporadaMapper.toResponseList(temporadaRepository.findByNombreContainingIgnoreCase(nombre));
    }

    public List<TemporadaResponse> findByFechaInicioBefore(LocalDate fechaInicio) {
        return temporadaMapper.toResponseList(temporadaRepository.findByFechaInicioBefore(fechaInicio));
    }

    public List<TemporadaResponse> findByFechaFinAfter(LocalDate fechaFin) {
        return temporadaMapper.toResponseList(temporadaRepository.findByFechaFinAfter(fechaFin));
    }

    public TemporadaResponse create(TemporadaRequest request) {
        validarCodigoUnico(request.getCodigo());
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        Temporada temporada = temporadaMapper.toEntity(request);
        Temporada temporadaGuardada = temporadaRepository.save(temporada);

        return temporadaMapper.toResponse(temporadaGuardada);
    }

    public TemporadaResponse update(Long id, TemporadaRequest request) {
        Temporada temporada = getTemporadaById(id);

        if (!temporada.getCodigo().equalsIgnoreCase(request.getCodigo())) {
            validarCodigoUnico(request.getCodigo());
        }

        validarFechas(request.getFechaInicio(), request.getFechaFin());

        temporadaMapper.updateEntity(request, temporada);
        Temporada temporadaActualizada = temporadaRepository.save(temporada);

        return temporadaMapper.toResponse(temporadaActualizada);
    }

    public void deleteById(Long id) {
        Temporada temporada = getTemporadaById(id);
        temporadaRepository.delete(temporada);
    }

    private Temporada getTemporadaById(Long id) {
        return temporadaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Temporada no encontrada con id: " + id));
    }

    private void validarCodigoUnico(String codigo) {
        if (temporadaRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe una temporada con codigo: " + codigo);
        }
    }

    private void validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin debe ser igual o posterior a la fecha de inicio");
        }
    }
}
