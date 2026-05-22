package cl.hilton.housekeeping.service;

import cl.hilton.housekeeping.dto.AsignacionRequest;
import cl.hilton.housekeeping.dto.AsignacionResponse;
import cl.hilton.housekeeping.mapper.AsignacionMapper;
import cl.hilton.housekeeping.model.Asignacion;
import cl.hilton.housekeeping.model.ProjHabitacion;
import cl.hilton.housekeeping.model.Tarea;
import cl.hilton.housekeeping.repository.AsignacionRepository;
import cl.hilton.housekeeping.repository.ProjHabitacionRepository;
import cl.hilton.housekeeping.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AsignacionService {

    private final AsignacionRepository asignacionRepository;
    private final ProjHabitacionRepository habitacionRepository;
    private final TareaRepository tareaRepository;
    private final AsignacionMapper asignacionMapper;

    public AsignacionService(
            AsignacionRepository asignacionRepository,
            ProjHabitacionRepository habitacionRepository,
            TareaRepository tareaRepository,
            AsignacionMapper asignacionMapper
    ) {
        this.asignacionRepository = asignacionRepository;
        this.habitacionRepository = habitacionRepository;
        this.tareaRepository = tareaRepository;
        this.asignacionMapper = asignacionMapper;
    }

    public List<AsignacionResponse> listar() {
        return asignacionRepository.findAll().stream()
                .map(asignacionMapper::toResponse)
                .toList();
    }

    public AsignacionResponse buscarPorId(Long id) {
        return asignacionMapper.toResponse(obtenerAsignacion(id));
    }

    public List<AsignacionResponse> buscarPorHabitacion(String numeroHabitacion) {
        return asignacionRepository.findByHabitacionNumeroHabitacion(numeroHabitacion).stream()
                .map(asignacionMapper::toResponse)
                .toList();
    }

    public List<AsignacionResponse> buscarPorTarea(String codigoTarea) {
        return asignacionRepository.findByTareaCodigo(codigoTarea).stream()
                .map(asignacionMapper::toResponse)
                .toList();
    }

    public List<AsignacionResponse> buscarPorCamarero(String emailCamarero) {
        return asignacionRepository.findByEmailCamarero(emailCamarero).stream()
                .map(asignacionMapper::toResponse)
                .toList();
    }

    public List<AsignacionResponse> buscarPorFecha(LocalDate fechaProgramada) {
        return asignacionRepository.findByFechaProgramada(fechaProgramada).stream()
                .map(asignacionMapper::toResponse)
                .toList();
    }

    public List<AsignacionResponse> buscarPorEstado(String estado) {
        return asignacionRepository.findByEstado(estado).stream()
                .map(asignacionMapper::toResponse)
                .toList();
    }

    public AsignacionResponse crear(AsignacionRequest request) {
        ProjHabitacion habitacion = obtenerHabitacion(request.getNumeroHabitacion());
        Tarea tarea = obtenerTarea(request.getCodigoTarea());

        Asignacion asignacion = asignacionMapper.toEntity(request, habitacion, tarea);

        return asignacionMapper.toResponse(asignacionRepository.save(asignacion));
    }

    public AsignacionResponse actualizar(Long id, AsignacionRequest request) {
        Asignacion asignacion = obtenerAsignacion(id);
        ProjHabitacion habitacion = obtenerHabitacion(request.getNumeroHabitacion());
        Tarea tarea = obtenerTarea(request.getCodigoTarea());

        asignacionMapper.updateEntity(asignacion, request, habitacion, tarea);

        return asignacionMapper.toResponse(asignacionRepository.save(asignacion));
    }

    public AsignacionResponse cambiarEstado(Long id, String estado) {
        Asignacion asignacion = obtenerAsignacion(id);
        asignacion.setEstado(estado);

        return asignacionMapper.toResponse(asignacionRepository.save(asignacion));
    }

    public void eliminar(Long id) {
        Asignacion asignacion = obtenerAsignacion(id);
        asignacionRepository.delete(asignacion);
    }

    private Asignacion obtenerAsignacion(Long id) {
        return asignacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
    }

    private ProjHabitacion obtenerHabitacion(String numeroHabitacion) {
        return habitacionRepository.findById(numeroHabitacion)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    }

    private Tarea obtenerTarea(String codigoTarea) {
        return tareaRepository.findByCodigo(codigoTarea)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }
}