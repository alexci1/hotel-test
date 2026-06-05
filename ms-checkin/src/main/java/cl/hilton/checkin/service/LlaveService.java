package cl.hilton.checkin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.checkin.dto.LlaveRequest;
import cl.hilton.checkin.dto.LlaveResponse;
import cl.hilton.checkin.mapper.LlaveMapper;
import cl.hilton.checkin.model.Llave;
import cl.hilton.checkin.model.ProjReserva;
import cl.hilton.checkin.repository.LlaveRepository;
import cl.hilton.checkin.repository.ProjReservaRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class LlaveService {

    private final LlaveRepository llaveRepository;
    private final ProjReservaRepository reservaRepository;
    private final LlaveMapper llaveMapper;

    public List<LlaveResponse> findAll() {
        return llaveMapper.toResponseList(llaveRepository.findAll());
    }

    public LlaveResponse findById(Long id) {
        Llave llave = getLlaveById(id);
        return llaveMapper.toResponse(llave);
    }

    public LlaveResponse findByCodigoLlave(String codigoLlave) {
        String codigo = validarTexto(codigoLlave, "codigoLlave");

        Llave llave = llaveRepository.findByCodigoLlave(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Llave no encontrada: " + codigo));

        return llaveMapper.toResponse(llave);
    }

    public List<LlaveResponse> findByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        return llaveMapper.toResponseList(llaveRepository.findByNumeroHabitacion(numero));
    }

    public List<LlaveResponse> findByActiva(Boolean activa) {
        Boolean estado = validarBoolean(activa, "activa");
        return llaveMapper.toResponseList(llaveRepository.findByActiva(estado));
    }

    public List<LlaveResponse> findByCodigoReserva(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");
        return llaveMapper.toResponseList(llaveRepository.findByReservaCodigoReserva(codigo));
    }

    @Transactional
    public LlaveResponse create(LlaveRequest request) {
        String codigoLlave = validarTexto(request.getCodigoLlave(), "codigoLlave");

        validarCodigoLlaveUnico(codigoLlave);

        ProjReserva reserva = getReservaOpcional(request.getCodigoReserva());
        Llave llave = llaveMapper.toEntity(request, reserva);
        llave.setActiva(request.getActiva() != null ? request.getActiva() : Boolean.TRUE);
        llave.setEmitidaEn(LocalDate.now());

        Llave llaveGuardada = llaveRepository.save(llave);

        return llaveMapper.toResponse(llaveGuardada);
    }

    @Transactional
    public LlaveResponse update(Long id, LlaveRequest request) {
        Long llaveId = validarId(id);
        String codigoLlave = validarTexto(request.getCodigoLlave(), "codigoLlave");

        Llave llave = getLlaveById(llaveId);

        llaveRepository.findByCodigoLlave(codigoLlave)
                .filter(existente -> !existente.getId().equals(llaveId))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe una llave con codigo: " + codigoLlave);
                });

        ProjReserva reserva = getReservaOpcional(request.getCodigoReserva());
        Boolean activaActual = llave.getActiva();

        llaveMapper.updateEntity(request, reserva, llave);
        llave.setActiva(request.getActiva() != null ? request.getActiva() : activaActual);

        Llave llaveActualizada = llaveRepository.save(llave);

        return llaveMapper.toResponse(llaveActualizada);
    }

    @Transactional
    public LlaveResponse updateEstado(Long id, Boolean activa) {
        Long llaveId = validarId(id);
        Boolean estado = validarBoolean(activa, "activa");

        Llave llave = getLlaveById(llaveId);
        llave.setActiva(estado);

        Llave llaveActualizada = llaveRepository.save(llave);

        return llaveMapper.toResponse(llaveActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long llaveId = validarId(id);
        getLlaveById(llaveId);
        llaveRepository.deleteById(llaveId);
    }

    private Llave getLlaveById(Long id) {
        Long llaveId = validarId(id);

        return llaveRepository.findById(llaveId)
                .orElseThrow(() -> new EntityNotFoundException("Llave no encontrada con id: " + llaveId));
    }

    private ProjReserva getReservaOpcional(String codigoReserva) {
        if (codigoReserva == null || codigoReserva.isBlank()) {
            return null;
        }

        return reservaRepository.findById(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada: " + codigoReserva));
    }

    private void validarCodigoLlaveUnico(String codigoLlave) {
        if (llaveRepository.existsByCodigoLlave(codigoLlave)) {
            throw new IllegalArgumentException("Ya existe una llave con codigo: " + codigoLlave);
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
