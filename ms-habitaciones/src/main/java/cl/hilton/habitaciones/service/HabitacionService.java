package cl.hilton.habitaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.habitaciones.client.TarifaLookupClient;
import cl.hilton.habitaciones.dto.HabitacionRequest;
import cl.hilton.habitaciones.dto.HabitacionResponse;
import cl.hilton.habitaciones.event.HabitacionEventProducer;
import cl.hilton.habitaciones.mapper.HabitacionMapper;
import cl.hilton.habitaciones.model.Habitacion;
import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.repository.EstadoHabitacionRepository;
import cl.hilton.habitaciones.repository.HabitacionRepository;
import cl.hilton.habitaciones.repository.TipoHabitacionRepository;
import cl.hilton.common.event.HabitacionCreatedEvent;
import cl.hilton.common.exception.DuplicateResourceException;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final TipoHabitacionRepository tipoHabitacionRepository;
    private final EstadoHabitacionRepository estadoHabitacionRepository;
    private final HabitacionMapper habitacionMapper;
    private final HabitacionEventProducer habitacionEventProducer;
    private final TarifaLookupClient tarifaLookupClient;

    public List<HabitacionResponse> findAll() {
        return habitacionMapper.toResponseList(habitacionRepository.findAll());
    }

    public HabitacionResponse findById(Long id) {
        Habitacion habitacion = getHabitacionById(id);
        return habitacionMapper.toResponse(habitacion);
    }

    public HabitacionResponse findByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        Habitacion habitacion = habitacionRepository.findByNumeroHabitacion(numero)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada con numero: " + numero));

        return habitacionMapper.toResponse(habitacion);
    }

    public List<HabitacionResponse> findByPiso(Integer piso) {
        Integer pisoValido = validarInteger(piso, "piso");
        return habitacionMapper.toResponseList(habitacionRepository.findByPiso(pisoValido));
    }

    public List<HabitacionResponse> findByActiva(Boolean activa) {
        Boolean estado = validarBoolean(activa, "activa");
        return habitacionMapper.toResponseList(habitacionRepository.findByActiva(estado));
    }

    public List<HabitacionResponse> findByCodigoTipo(String codigoTipo) {
        String codigo = validarTexto(codigoTipo, "codigoTipo");
        return habitacionMapper.toResponseList(habitacionRepository.findByTipoHabitacionCodigo(codigo));
    }

    public List<HabitacionResponse> findByCodigoTipoAndActiva(String codigoTipo, Boolean activa) {
        String codigo = validarTexto(codigoTipo, "codigoTipo");
        Boolean estado = validarBoolean(activa, "activa");
        return habitacionMapper.toResponseList(habitacionRepository.findByTipoHabitacionCodigoAndActiva(codigo, estado));
    }

    @Transactional
    public HabitacionResponse create(HabitacionRequest request) {
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");
        String codigoTipo = validarTexto(request.getCodigoTipo(), "codigoTipo");

        validarNumeroHabitacionUnico(numeroHabitacion);

        TipoHabitacion tipoHabitacion = getTipoHabitacionByCodigo(codigoTipo);

        validarExisteTarifaActivaParaTipo(codigoTipo);

        Habitacion habitacion = habitacionMapper.toEntity(request);
        habitacion.setTipoHabitacion(tipoHabitacion);
        habitacion.setActiva(request.getActiva() != null ? request.getActiva() : true);

        Habitacion habitacionGuardada = habitacionRepository.save(habitacion);
        HabitacionCreatedEvent event = new HabitacionCreatedEvent(
            habitacion.getId(), 
            habitacion.getNumeroHabitacion(), 
            habitacion.getPiso(), 
            habitacion.getTipoHabitacion().getCodigo(), 
            habitacion.getActiva());

        habitacionEventProducer.sendCreated(habitacion.getNumeroHabitacion(), event);

        return habitacionMapper.toResponse(habitacionGuardada);
    }

    @Transactional
    public HabitacionResponse update(Long id, HabitacionRequest request) {
        Long habitacionId = validarId(id);
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");
        String codigoTipo = validarTexto(request.getCodigoTipo(), "codigoTipo");

        Habitacion habitacion = getHabitacionById(habitacionId);
        Boolean activaActual = habitacion.getActiva();

        if (!habitacion.getNumeroHabitacion().equalsIgnoreCase(numeroHabitacion)) {
            validarNumeroHabitacionUnico(numeroHabitacion);
        }

        TipoHabitacion tipoHabitacion = getTipoHabitacionByCodigo(codigoTipo);

        habitacionMapper.updateEntity(request, habitacion);
        habitacion.setTipoHabitacion(tipoHabitacion);
        habitacion.setActiva(request.getActiva() != null ? request.getActiva() : activaActual);

        Habitacion habitacionActualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionActualizada);
    }

    @Transactional
    public HabitacionResponse cambiarActiva(Long id, Boolean activa) {
        Long habitacionId = validarId(id);
        Boolean estado = validarBoolean(activa, "activa");

        Habitacion habitacion = getHabitacionById(habitacionId);
        habitacion.setActiva(estado);

        Habitacion habitacionActualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(habitacionActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long habitacionId = validarId(id);
        Habitacion habitacion = getHabitacionById(habitacionId);

        estadoHabitacionRepository
                .findByHabitacionNumeroHabitacion(habitacion.getNumeroHabitacion())
                .ifPresent(estadoHabitacionRepository::delete);

        estadoHabitacionRepository.flush();
        habitacionRepository.delete(habitacion);
    }

    private Habitacion getHabitacionById(Long id) {
        Long habitacionId = validarId(id);

        return habitacionRepository.findById(habitacionId)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada con id: " + habitacionId));
    }

    private TipoHabitacion getTipoHabitacionByCodigo(String codigoTipo) {
        String codigo = validarTexto(codigoTipo, "codigoTipo");

        return tipoHabitacionRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion no encontrado con codigo: " + codigo));
    }

    private void validarNumeroHabitacionUnico(String numeroHabitacion) {
        if (habitacionRepository.existsByNumeroHabitacion(numeroHabitacion)) {
            log.warn("Intento de guardar habitacion con numero duplicado: {}", numeroHabitacion);

            throw new DuplicateResourceException(
                    "Habitacion",
                    "numeroHabitacion",
                    numeroHabitacion,
                    "Numero de habitacion duplicado"
            );
        }
    }

    private void validarExisteTarifaActivaParaTipo(String codigoTipo) {
        boolean existeTarifaActiva = tarifaLookupClient.existsTarifaActivaByTipoHabitacion(codigoTipo);

        if (!existeTarifaActiva) {
            log.warn("No se encontro tarifa activa para el tipo de habitacion: {}", codigoTipo);
            throw new EntityNotFoundException("No existe una tarifa activa para el tipo de habitacion: " + codigoTipo);
        }
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
