package cl.hilton.inventario.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.inventario.client.HabitacionClient;
import cl.hilton.inventario.dto.HabitacionInventarioResponse;
import cl.hilton.inventario.dto.ProjHabitacionRequest;
import cl.hilton.inventario.dto.ProjHabitacionResponse;
import cl.hilton.inventario.mapper.ProjHabitacionMapper;
import cl.hilton.inventario.model.ProjHabitacion;
import cl.hilton.inventario.repository.ProjHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProjHabitacionService {

    private final ProjHabitacionRepository habitacionRepository;
    private final ProjHabitacionMapper habitacionMapper;
    private final HabitacionClient habitacionClient;

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

    public List<ProjHabitacionResponse> findByActualizadoEn(LocalDate actualizadoEn) {
        LocalDate fecha = validarFecha(actualizadoEn, "actualizadoEn");
        return habitacionMapper.toResponseList(habitacionRepository.findByActualizadoEn(fecha));
    }

    @Transactional
    public ProjHabitacionResponse create(ProjHabitacionRequest request) {
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");
        validarNumeroUnico(numeroHabitacion);

        ProjHabitacion habitacion = habitacionMapper.toEntity(request);
        habitacion.setActualizadoEn(LocalDate.now());

        ProjHabitacion habitacionGuardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionGuardada);
    }

    @Transactional
    public ProjHabitacionResponse update(String numeroHabitacion, ProjHabitacionRequest request) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        ProjHabitacion habitacion = getHabitacionByNumero(numero);

        habitacionMapper.updateEntity(request, habitacion);
        habitacion.setNumeroHabitacion(numero);
        habitacion.setActualizadoEn(LocalDate.now());

        ProjHabitacion habitacionActualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionActualizada);
    }

    @Transactional
    public ProjHabitacionResponse sincronizarPorNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        HabitacionInventarioResponse externa = habitacionClient.buscarPorNumeroHabitacion(numero);
        ProjHabitacion habitacion = habitacionRepository.findByNumeroHabitacion(externa.getNumeroHabitacion())
                .orElseGet(ProjHabitacion::new);

        habitacion.setNumeroHabitacion(externa.getNumeroHabitacion());
        habitacion.setTipo(externa.getCodigoTipo());
        habitacion.setActualizadoEn(LocalDate.now());

        ProjHabitacion habitacionGuardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionGuardada);
    }

    @Transactional
    public void deleteByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        getHabitacionByNumero(numero);
        habitacionRepository.deleteById(numero);
    }

    private ProjHabitacion getHabitacionByNumero(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        return habitacionRepository.findByNumeroHabitacion(numero)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada con numero: " + numero));
    }

    private void validarNumeroUnico(String numeroHabitacion) {
        if (habitacionRepository.existsByNumeroHabitacion(numeroHabitacion)) {
            throw new IllegalArgumentException("Ya existe una habitacion proyectada con numero: " + numeroHabitacion);
        }
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
