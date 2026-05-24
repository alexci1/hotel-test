package cl.hilton.housekeeping.service;

import cl.hilton.housekeeping.dto.TareaRequest;
import cl.hilton.housekeeping.dto.TareaResponse;
import cl.hilton.housekeeping.mapper.TareaMapper;
import cl.hilton.housekeeping.model.Tarea;
import cl.hilton.housekeeping.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final TareaMapper tareaMapper;

    public TareaService(TareaRepository tareaRepository, TareaMapper tareaMapper) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
    }

    public List<TareaResponse> listar() {
        return tareaRepository.findAll().stream()
                .map(tareaMapper::toResponse)
                .toList();
    }

    public TareaResponse buscarPorId(Long id) {
        return tareaMapper.toResponse(obtenerTarea(id));
    }

    public TareaResponse buscarPorCodigo(String codigo) {
        Tarea tarea = tareaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        return tareaMapper.toResponse(tarea);
    }

    public List<TareaResponse> buscarPorActiva(Boolean activa) {
        return tareaRepository.findByActiva(activa).stream()
                .map(tareaMapper::toResponse)
                .toList();
    }

    public List<TareaResponse> buscarPorDescripcion(String descripcion) {
        return tareaRepository.findByDescripcionContainingIgnoreCase(descripcion).stream()
                .map(tareaMapper::toResponse)
                .toList();
    }

    public TareaResponse crear(TareaRequest request) {
        if (tareaRepository.existsByCodigo(request.getCodigo())) {
            throw new RuntimeException("Ya existe una tarea con ese código");
        }

        Tarea tarea = tareaMapper.toEntity(request);

        return tareaMapper.toResponse(tareaRepository.save(tarea));
    }

    public TareaResponse actualizar(Long id, TareaRequest request) {
        Tarea tarea = obtenerTarea(id);

        tareaMapper.updateEntity(tarea, request);

        return tareaMapper.toResponse(tareaRepository.save(tarea));
    }

    public void eliminar(Long id) {
        Tarea tarea = obtenerTarea(id);
        tareaRepository.delete(tarea);
    }

    private Tarea obtenerTarea(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }
}