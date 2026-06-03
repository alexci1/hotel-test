package cl.hilton.habitaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.habitaciones.dto.EstadoHabitacionRequest;
import cl.hilton.habitaciones.dto.EstadoHabitacionResponse;
import cl.hilton.habitaciones.mapper.EstadoHabitacionMapper;
import cl.hilton.habitaciones.model.EstadoHabitacion;
import cl.hilton.habitaciones.model.Habitacion;
import cl.hilton.habitaciones.repository.EstadoHabitacionRepository;
import cl.hilton.habitaciones.repository.HabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class EstadoHabitacionService {

    private final EstadoHabitacionRepository estadoHabitacionRepository;
    private final HabitacionRepository habitacionRepository;
    private final EstadoHabitacionMapper estadoHabitacionMapper;

    public List<EstadoHabitacionResponse> findAll() {
        return estadoHabitacionMapper.toResponseList(estadoHabitacionRepository.findAll());
    }

    public EstadoHabitacionResponse findById(Long id) {
        EstadoHabitacion estadoHabitacion = getEstadoHabitacionById(id);
        return estadoHabitacionMapper.toResponse(estadoHabitacion);
    }

    public EstadoHabitacionResponse findByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        EstadoHabitacion estadoHabitacion = estadoHabitacionRepository.findByHabitacionNumeroHabitacion(numero)
                .orElseThrow(() -> new EntityNotFoundException("Estado no encontrado para habitacion: " + numero));

        return estadoHabitacionMapper.toResponse(estadoHabitacion);
    }

    public List<EstadoHabitacionResponse> findByEstado(String estado) {
        String estadoValido = validarTexto(estado, "estado");
        return estadoHabitacionMapper.toResponseList(estadoHabitacionRepository.findByEstado(estadoValido));
    }

    public List<EstadoHabitacionResponse> findByActualizadoEn(LocalDate actualizadoEn) {
        LocalDate fecha = validarFecha(actualizadoEn, "actualizadoEn");
        return estadoHabitacionMapper.toResponseList(estadoHabitacionRepository.findByActualizadoEn(fecha));
    }

    @Transactional
    public EstadoHabitacionResponse create(EstadoHabitacionRequest request) {
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");

        if (estadoHabitacionRepository.existsByHabitacionNumeroHabitacion(numeroHabitacion)) {
            throw new IllegalArgumentException("Ya existe estado para la habitacion: " + numeroHabitacion);
        }

        Habitacion habitacion = getHabitacionByNumero(numeroHabitacion);

        EstadoHabitacion estadoHabitacion = estadoHabitacionMapper.toEntity(request);
        estadoHabitacion.setHabitacion(habitacion);
        estadoHabitacion.setEstado(request.getEstado() != null ? request.getEstado() : "LIMPIA");
        estadoHabitacion.setActualizadoEn(LocalDate.now());

        EstadoHabitacion estadoGuardado = estadoHabitacionRepository.save(estadoHabitacion);

        return estadoHabitacionMapper.toResponse(estadoGuardado);
    }

    @Transactional
    public EstadoHabitacionResponse update(Long id, EstadoHabitacionRequest request) {
        Long estadoId = validarId(id);
        EstadoHabitacion estadoHabitacion = getEstadoHabitacionById(estadoId);
        String estadoActual = estadoHabitacion.getEstado();

        if (request.getNumeroHabitacion() != null && !request.getNumeroHabitacion().isBlank()) {
            String numeroHabitacion = request.getNumeroHabitacion();

            if (!estadoHabitacion.getHabitacion().getNumeroHabitacion().equalsIgnoreCase(numeroHabitacion)) {
                if (estadoHabitacionRepository.existsByHabitacionNumeroHabitacion(numeroHabitacion)) {
                    throw new IllegalArgumentException("Ya existe estado para la habitacion: " + numeroHabitacion);
                }

                Habitacion habitacion = getHabitacionByNumero(numeroHabitacion);
                estadoHabitacion.setHabitacion(habitacion);
            }
        }

        estadoHabitacionMapper.updateEntity(request, estadoHabitacion);
        estadoHabitacion.setEstado(request.getEstado() != null ? request.getEstado() : estadoActual);
        estadoHabitacion.setActualizadoEn(LocalDate.now());

        EstadoHabitacion estadoActualizado = estadoHabitacionRepository.save(estadoHabitacion);

        return estadoHabitacionMapper.toResponse(estadoActualizado);
    }

    @Transactional
    public EstadoHabitacionResponse cambiarEstado(String numeroHabitacion, String estado) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        String estadoValido = validarTexto(estado, "estado");

        EstadoHabitacion estadoHabitacion = estadoHabitacionRepository.findByHabitacionNumeroHabitacion(numero)
                .orElseThrow(() -> new EntityNotFoundException("Estado no encontrado para habitacion: " + numero));

        estadoHabitacion.setEstado(estadoValido);
        estadoHabitacion.setActualizadoEn(LocalDate.now());

        EstadoHabitacion estadoActualizado = estadoHabitacionRepository.save(estadoHabitacion);

        return estadoHabitacionMapper.toResponse(estadoActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long estadoId = validarId(id);
        getEstadoHabitacionById(estadoId);
        estadoHabitacionRepository.deleteById(estadoId);
    }

    private EstadoHabitacion getEstadoHabitacionById(Long id) {
        Long estadoId = validarId(id);

        return estadoHabitacionRepository.findById(estadoId)
                .orElseThrow(() -> new EntityNotFoundException("Estado de habitacion no encontrado con id: " + estadoId));
    }

    private Habitacion getHabitacionByNumero(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        return habitacionRepository.findByNumeroHabitacion(numero)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada con numero: " + numero));
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
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
