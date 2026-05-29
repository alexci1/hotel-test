package cl.hilton.housekeeping.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.housekeeping.dto.AsignacionRequest;
import cl.hilton.housekeeping.dto.AsignacionResponse;
import cl.hilton.housekeeping.mapper.AsignacionMapper;
import cl.hilton.housekeeping.model.Asignacion;
import cl.hilton.housekeeping.model.ProjHabitacion;
import cl.hilton.housekeeping.model.Tarea;
import cl.hilton.housekeeping.repository.AsignacionRepository;
import cl.hilton.housekeeping.repository.ProjHabitacionRepository;
import cl.hilton.housekeeping.repository.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsignacionService {

    private final AsignacionRepository asignacionRepository;
    private final ProjHabitacionRepository habitacionRepository;
    private final TareaRepository tareaRepository;
    private final AsignacionMapper asignacionMapper;

    public List<AsignacionResponse> findAll() {
        return asignacionMapper.toResponseList(asignacionRepository.findAll());
    }

    public AsignacionResponse findById(Long id) {
        return asignacionMapper.toResponse(getAsignacion(id));
    }

    public List<AsignacionResponse> findByNumeroHabitacion(String numeroHabitacion) {
        return asignacionMapper.toResponseList(asignacionRepository.findByHabitacionNumeroHabitacion(numeroHabitacion));
    }

    public List<AsignacionResponse> findByCodigoTarea(String codigoTarea) {
        return asignacionMapper.toResponseList(asignacionRepository.findByTareaCodigo(codigoTarea));
    }

    public List<AsignacionResponse> findByEmailCamarero(String emailCamarero) {
        return asignacionMapper.toResponseList(asignacionRepository.findByEmailCamarero(emailCamarero));
    }

    public List<AsignacionResponse> findByFechaProgramada(LocalDate fechaProgramada) {
        return asignacionMapper.toResponseList(asignacionRepository.findByFechaProgramada(fechaProgramada));
    }

    public List<AsignacionResponse> findByEstado(String estado) {
        return asignacionMapper.toResponseList(asignacionRepository.findByEstado(estado));
    }

    public List<AsignacionResponse> findByPrioridad(Integer prioridad) {
        return asignacionMapper.toResponseList(asignacionRepository.findByPrioridad(prioridad));
    }

    public AsignacionResponse create(AsignacionRequest request) {
        ProjHabitacion habitacion = getHabitacion(request.getNumeroHabitacion());
        Tarea tarea = getTarea(request.getCodigoTarea());

        Asignacion asignacion = asignacionMapper.toEntity(request, habitacion, tarea);
        asignacion.setEstado(request.getEstado() != null ? request.getEstado() : "PENDIENTE");
        asignacion.setPrioridad(request.getPrioridad() != null ? request.getPrioridad() : 1);

        Asignacion saved = asignacionRepository.save(asignacion);
        return asignacionMapper.toResponse(saved);
    }

    public AsignacionResponse update(Long id, AsignacionRequest request) {
        Asignacion asignacion = getAsignacion(id);
        ProjHabitacion habitacion = getHabitacion(request.getNumeroHabitacion());
        Tarea tarea = getTarea(request.getCodigoTarea());

        asignacionMapper.updateEntity(request, habitacion, tarea, asignacion);
        asignacion.setEstado(request.getEstado() != null ? request.getEstado() : "PENDIENTE");
        asignacion.setPrioridad(request.getPrioridad() != null ? request.getPrioridad() : 1);

        Asignacion saved = asignacionRepository.save(asignacion);
        return asignacionMapper.toResponse(saved);
    }

    public AsignacionResponse updateEstado(Long id, String estado) {
        Asignacion asignacion = getAsignacion(id);
        asignacion.setEstado(estado);

        Asignacion saved = asignacionRepository.save(asignacion);
        return asignacionMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Asignacion asignacion = getAsignacion(id);
        asignacionRepository.delete(asignacion);
    }

    private Asignacion getAsignacion(Long id) {
        return asignacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asignacion no encontrada: " + id));
    }

    private ProjHabitacion getHabitacion(String numeroHabitacion) {
        return habitacionRepository.findById(numeroHabitacion)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada: " + numeroHabitacion));
    }

    private Tarea getTarea(String codigoTarea) {
        return tareaRepository.findByCodigo(codigoTarea)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada: " + codigoTarea));
    }
}