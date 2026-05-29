package cl.hilton.housekeeping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.housekeeping.dto.TareaRequest;
import cl.hilton.housekeeping.dto.TareaResponse;
import cl.hilton.housekeeping.mapper.TareaMapper;
import cl.hilton.housekeeping.model.Tarea;
import cl.hilton.housekeeping.repository.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class TareaService {

    private final TareaRepository tareaRepository;
    private final TareaMapper tareaMapper;

    public List<TareaResponse> findAll() {
        return tareaMapper.toResponseList(tareaRepository.findAll());
    }

    public TareaResponse findById(Long id) {
        return tareaMapper.toResponse(getTarea(id));
    }

    public TareaResponse findByCodigo(String codigo) {
        Tarea tarea = tareaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada: " + codigo));
        return tareaMapper.toResponse(tarea);
    }

    public List<TareaResponse> findByActiva(Boolean activa) {
        return tareaMapper.toResponseList(tareaRepository.findByActiva(activa));
    }

    public List<TareaResponse> findByDescripcion(String descripcion) {
        return tareaMapper.toResponseList(tareaRepository.findByDescripcionContainingIgnoreCase(descripcion));
    }

    public TareaResponse create(TareaRequest request) {
        if (tareaRepository.existsByCodigo(request.getCodigo())) {
            throw new IllegalArgumentException("Ya existe una tarea con codigo: " + request.getCodigo());
        }

        Tarea tarea = tareaMapper.toEntity(request);
        tarea.setActiva(request.getActiva() != null ? request.getActiva() : Boolean.TRUE);

        Tarea saved = tareaRepository.save(tarea);
        return tareaMapper.toResponse(saved);
    }

    public TareaResponse update(Long id, TareaRequest request) {
        Tarea tarea = getTarea(id);

        tareaRepository.findByCodigo(request.getCodigo())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe una tarea con codigo: " + request.getCodigo());
                });

        tareaMapper.updateEntity(request, tarea);
        tarea.setActiva(request.getActiva() != null ? request.getActiva() : Boolean.TRUE);

        Tarea saved = tareaRepository.save(tarea);
        return tareaMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Tarea tarea = getTarea(id);
        tareaRepository.delete(tarea);
    }

    private Tarea getTarea(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada: " + id));
    }
}