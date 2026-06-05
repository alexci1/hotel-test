package cl.hilton.housekeeping.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.housekeeping.dto.TareaRequest;
import cl.hilton.housekeeping.dto.TareaResponse;
import cl.hilton.housekeeping.mapper.TareaMapper;
import cl.hilton.housekeeping.model.Tarea;
import cl.hilton.housekeeping.repository.TareaRepository;
import cl.hilton.common.exception.EntityNotFoundException;
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
        Tarea tarea = getTareaById(id);
        return tareaMapper.toResponse(tarea);
    }

    public TareaResponse findByCodigo(String codigo) {
        String codigoValido = validarTexto(codigo, "codigo");

        Tarea tarea = tareaRepository.findByCodigo(codigoValido)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con codigo: " + codigoValido));

        return tareaMapper.toResponse(tarea);
    }

    public List<TareaResponse> findByActiva(Boolean activa) {
        Boolean estado = validarBoolean(activa, "activa");
        return tareaMapper.toResponseList(tareaRepository.findByActiva(estado));
    }

    public List<TareaResponse> findByDescripcion(String descripcion) {
        String texto = validarTexto(descripcion, "descripcion");
        return tareaMapper.toResponseList(tareaRepository.findByDescripcionContainingIgnoreCase(texto));
    }

    @Transactional
    public TareaResponse create(TareaRequest request) {
        String codigo = validarTexto(request.getCodigo(), "codigo");

        validarCodigoUnico(codigo);

        Tarea tarea = tareaMapper.toEntity(request);
        tarea.setActiva(request.getActiva() != null ? request.getActiva() : Boolean.TRUE);

        Tarea tareaGuardada = tareaRepository.save(tarea);

        return tareaMapper.toResponse(tareaGuardada);
    }

    @Transactional
    public TareaResponse update(Long id, TareaRequest request) {
        Long tareaId = validarId(id);
        String codigo = validarTexto(request.getCodigo(), "codigo");

        Tarea tarea = getTareaById(tareaId);
        Boolean activaActual = tarea.getActiva();

        tareaRepository.findByCodigo(codigo)
                .filter(existente -> !existente.getId().equals(tareaId))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe una tarea con codigo: " + codigo);
                });

        tareaMapper.updateEntity(request, tarea);
        tarea.setActiva(request.getActiva() != null ? request.getActiva() : activaActual);

        Tarea tareaActualizada = tareaRepository.save(tarea);

        return tareaMapper.toResponse(tareaActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long tareaId = validarId(id);
        getTareaById(tareaId);
        tareaRepository.deleteById(tareaId);
    }

    private Tarea getTareaById(Long id) {
        Long tareaId = validarId(id);

        return tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con id: " + tareaId));
    }

    private void validarCodigoUnico(String codigo) {
        if (tareaRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe una tarea con codigo: " + codigo);
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
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
