package cl.hilton.housekeeping.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@SuppressWarnings("null")
public class AsignacionService {

    private final AsignacionRepository asignacionRepository;
    private final ProjHabitacionRepository habitacionRepository;
    private final TareaRepository tareaRepository;
    private final AsignacionMapper asignacionMapper;

    public List<AsignacionResponse> findAll() {
        return asignacionMapper.toResponseList(asignacionRepository.findAll());
    }

    public AsignacionResponse findById(Long id) {
        Asignacion asignacion = getAsignacionById(id);
        return asignacionMapper.toResponse(asignacion);
    }

    public List<AsignacionResponse> findByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        return asignacionMapper.toResponseList(asignacionRepository.findByHabitacionNumeroHabitacion(numero));
    }

    public List<AsignacionResponse> findByCodigoTarea(String codigoTarea) {
        String codigo = validarTexto(codigoTarea, "codigoTarea");
        return asignacionMapper.toResponseList(asignacionRepository.findByTareaCodigo(codigo));
    }

    public List<AsignacionResponse> findByEmailCamarero(String emailCamarero) {
        String email = validarTexto(emailCamarero, "emailCamarero");
        return asignacionMapper.toResponseList(asignacionRepository.findByEmailCamarero(email));
    }

    public List<AsignacionResponse> findByFechaProgramada(LocalDate fechaProgramada) {
        LocalDate fecha = validarFecha(fechaProgramada, "fechaProgramada");
        return asignacionMapper.toResponseList(asignacionRepository.findByFechaProgramada(fecha));
    }

    public List<AsignacionResponse> findByEstado(String estado) {
        String estadoValido = validarTexto(estado, "estado");
        return asignacionMapper.toResponseList(asignacionRepository.findByEstado(estadoValido));
    }

    public List<AsignacionResponse> findByPrioridad(Integer prioridad) {
        Integer prioridadValida = validarInteger(prioridad, "prioridad");
        return asignacionMapper.toResponseList(asignacionRepository.findByPrioridad(prioridadValida));
    }

    @Transactional
    public AsignacionResponse create(AsignacionRequest request) {
        ProjHabitacion habitacion = getHabitacionByNumero(request.getNumeroHabitacion());
        Tarea tarea = getTareaByCodigo(request.getCodigoTarea());

        Asignacion asignacion = asignacionMapper.toEntity(request, habitacion, tarea);
        asignacion.setEstado(request.getEstado() != null ? request.getEstado() : "PENDIENTE");
        asignacion.setPrioridad(request.getPrioridad() != null ? request.getPrioridad() : 1);

        Asignacion asignacionGuardada = asignacionRepository.save(asignacion);

        return asignacionMapper.toResponse(asignacionGuardada);
    }

    @Transactional
    public AsignacionResponse update(Long id, AsignacionRequest request) {
        Long asignacionId = validarId(id);

        Asignacion asignacion = getAsignacionById(asignacionId);
        ProjHabitacion habitacion = getHabitacionByNumero(request.getNumeroHabitacion());
        Tarea tarea = getTareaByCodigo(request.getCodigoTarea());

        String estadoActual = asignacion.getEstado();
        Integer prioridadActual = asignacion.getPrioridad();

        asignacionMapper.updateEntity(request, habitacion, tarea, asignacion);
        asignacion.setEstado(request.getEstado() != null ? request.getEstado() : estadoActual);
        asignacion.setPrioridad(request.getPrioridad() != null ? request.getPrioridad() : prioridadActual);

        Asignacion asignacionActualizada = asignacionRepository.save(asignacion);

        return asignacionMapper.toResponse(asignacionActualizada);
    }

    @Transactional
    public AsignacionResponse updateEstado(Long id, String estado) {
        Long asignacionId = validarId(id);
        String estadoValido = validarTexto(estado, "estado");

        Asignacion asignacion = getAsignacionById(asignacionId);
        asignacion.setEstado(estadoValido);

        Asignacion asignacionActualizada = asignacionRepository.save(asignacion);

        return asignacionMapper.toResponse(asignacionActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long asignacionId = validarId(id);
        getAsignacionById(asignacionId);
        asignacionRepository.deleteById(asignacionId);
    }

    private Asignacion getAsignacionById(Long id) {
        Long asignacionId = validarId(id);

        return asignacionRepository.findById(asignacionId)
                .orElseThrow(() -> new EntityNotFoundException("Asignacion no encontrada con id: " + asignacionId));
    }

    private ProjHabitacion getHabitacionByNumero(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        return habitacionRepository.findById(numero)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada: " + numero));
    }

    private Tarea getTareaByCodigo(String codigoTarea) {
        String codigo = validarTexto(codigoTarea, "codigoTarea");

        return tareaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada: " + codigo));
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

    private LocalDate validarFecha(LocalDate valor, String campo) {
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
