package cl.hilton.checkin.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.hilton.checkin.dto.LlaveRequest;
import cl.hilton.checkin.dto.LlaveResponse;
import cl.hilton.checkin.mapper.LlaveMapper;
import cl.hilton.checkin.model.Llave;
import cl.hilton.checkin.model.ProjReserva;
import cl.hilton.checkin.repository.LlaveRepository;
import cl.hilton.checkin.repository.ProjReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LlaveService {

    private final LlaveRepository llaveRepository;
    private final ProjReservaRepository reservaRepository;
    private final LlaveMapper llaveMapper;

    public List<LlaveResponse> findAll() {
        return llaveMapper.toResponseList(llaveRepository.findAll());
    }

    public LlaveResponse findById(Long id) {
        return llaveMapper.toResponse(getLlave(id));
    }

    public LlaveResponse findByCodigoLlave(String codigoLlave) {
        String codigo = Objects.requireNonNull(codigoLlave, "codigoLlave no puede ser null");

        Llave llave = llaveRepository.findByCodigoLlave(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Llave no encontrada: " + codigo));

        return llaveMapper.toResponse(llave);
    }

    public List<LlaveResponse> findByNumeroHabitacion(String numeroHabitacion) {
        String numero = Objects.requireNonNull(numeroHabitacion, "numeroHabitacion no puede ser null");
        return llaveMapper.toResponseList(llaveRepository.findByNumeroHabitacion(numero));
    }

    public List<LlaveResponse> findByActiva(Boolean activa) {
        Boolean estado = Objects.requireNonNull(activa, "activa no puede ser null");
        return llaveMapper.toResponseList(llaveRepository.findByActiva(estado));
    }

    public List<LlaveResponse> findByCodigoReserva(String codigoReserva) {
        String codigo = Objects.requireNonNull(codigoReserva, "codigoReserva no puede ser null");
        return llaveMapper.toResponseList(llaveRepository.findByReservaCodigoReserva(codigo));
    }

    public LlaveResponse create(LlaveRequest request) {
        String codigoLlave = Objects.requireNonNull(request.getCodigoLlave(), "codigoLlave no puede ser null");
        validateCodigoLlaveDisponible(codigoLlave);

        ProjReserva reserva = getReservaOpcional(request.getCodigoReserva());
        Llave llave = llaveMapper.toEntity(request, reserva);
        llave.setActiva(request.getActiva() != null ? request.getActiva() : Boolean.TRUE);
        llave.setEmitidaEn(LocalDate.now());

        Llave saved = llaveRepository.save(Objects.requireNonNull(llave));
        return llaveMapper.toResponse(saved);
    }

    public LlaveResponse update(Long id, LlaveRequest request) {
        Long llaveId = Objects.requireNonNull(id, "id no puede ser null");
        String codigoLlave = Objects.requireNonNull(request.getCodigoLlave(), "codigoLlave no puede ser null");

        Llave llave = getLlave(llaveId);

        llaveRepository.findByCodigoLlave(codigoLlave)
                .filter(existente -> !existente.getId().equals(llaveId))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe una llave con codigo: " + codigoLlave);
                });

        ProjReserva reserva = getReservaOpcional(request.getCodigoReserva());
        llaveMapper.updateEntity(request, reserva, llave);
        llave.setActiva(request.getActiva() != null ? request.getActiva() : Boolean.TRUE);

        Llave saved = llaveRepository.save(Objects.requireNonNull(llave));
        return llaveMapper.toResponse(saved);
    }

    public LlaveResponse updateEstado(Long id, Boolean activa) {
        Boolean estado = Objects.requireNonNull(activa, "activa no puede ser null");
        Llave llave = getLlave(id);
        llave.setActiva(estado);

        Llave saved = llaveRepository.save(Objects.requireNonNull(llave));
        return llaveMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Llave llave = getLlave(id);
        llaveRepository.delete(Objects.requireNonNull(llave));
    }

    private Llave getLlave(Long id) {
        Long llaveId = Objects.requireNonNull(id, "id no puede ser null");

        return llaveRepository.findById(llaveId)
                .orElseThrow(() -> new EntityNotFoundException("Llave no encontrada: " + llaveId));
    }

    private ProjReserva getReservaOpcional(String codigoReserva) {
        if (codigoReserva == null || codigoReserva.isBlank()) {
            return null;
        }

        return reservaRepository.findById(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada: " + codigoReserva));
    }

    private void validateCodigoLlaveDisponible(String codigoLlave) {
        String codigo = Objects.requireNonNull(codigoLlave, "codigoLlave no puede ser null");

        if (llaveRepository.existsByCodigoLlave(codigo)) {
            throw new IllegalArgumentException("Ya existe una llave con codigo: " + codigo);
        }
    }
}