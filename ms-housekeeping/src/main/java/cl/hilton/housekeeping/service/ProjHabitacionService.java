package cl.hilton.housekeeping.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.housekeeping.dto.ProjHabitacionRequest;
import cl.hilton.housekeeping.dto.ProjHabitacionResponse;
import cl.hilton.housekeeping.mapper.ProjHabitacionMapper;
import cl.hilton.housekeeping.model.ProjHabitacion;
import cl.hilton.housekeeping.repository.ProjHabitacionRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjHabitacionService {

    private final ProjHabitacionRepository habitacionRepository;
    private final ProjHabitacionMapper habitacionMapper;

    public List<ProjHabitacionResponse> findAll() {
        return habitacionMapper.toResponseList(habitacionRepository.findAll());
    }

    public ProjHabitacionResponse findByNumeroHabitacion(String numeroHabitacion) {
        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);
        return habitacionMapper.toResponse(habitacion);
    }

    public List<ProjHabitacionResponse> findByTipo(String tipo) {
        String tipoValido = validarTexto(tipo, "tipo");
        return habitacionMapper.toResponseList(habitacionRepository.findByTipo(tipoValido));
    }

    public List<ProjHabitacionResponse> findByPiso(Integer piso) {
        Integer pisoValido = validarInteger(piso, "piso");
        return habitacionMapper.toResponseList(habitacionRepository.findByPiso(pisoValido));
    }

    @Transactional
    public ProjHabitacionResponse create(ProjHabitacionRequest request) {
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");

        if (habitacionRepository.existsById(numeroHabitacion)) {
            throw new IllegalArgumentException("Ya existe una habitacion con numero: " + numeroHabitacion);
        }

        ProjHabitacion habitacion = habitacionMapper.toEntity(request);
        ProjHabitacion habitacionGuardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionGuardada);
    }

    @Transactional
    public ProjHabitacionResponse update(String numeroHabitacion, ProjHabitacionRequest request) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        ProjHabitacion habitacion = getHabitacionByNumero(numero);
        habitacionMapper.updateEntity(habitacion, request);

        ProjHabitacion habitacionActualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionActualizada);
    }

    @Transactional
    public void save(String numeroHabitacion, String codigoTipo, Integer piso) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        String tipo = validarTexto(codigoTipo, "codigoTipo");
        Integer pisoValido = validarInteger(piso, "piso");

        ProjHabitacion habitacion = habitacionRepository.findById(numero)
                .orElseGet(ProjHabitacion::new);

        habitacion.setNumeroHabitacion(numero);
        habitacion.setTipo(tipo);
        habitacion.setPiso(pisoValido);
        habitacion.setActualizadoEn(LocalDate.now());

        habitacionRepository.save(habitacion);
    }

    @Transactional
    public void deleteByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        getHabitacionByNumero(numero);
        habitacionRepository.deleteById(numero);
    }

    private ProjHabitacion getHabitacionByNumero(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        return habitacionRepository.findById(numero)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada: " + numero));
    }

    private Integer validarInteger(Integer valor, String campo) {
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