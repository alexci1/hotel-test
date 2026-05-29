package cl.hilton.checkin.service;

import java.time.LocalDate;
import java.util.List;

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
        Llave llave = llaveRepository.findByCodigoLlave(codigoLlave)
                .orElseThrow(() -> new EntityNotFoundException("Llave no encontrada: " + codigoLlave));

        return llaveMapper.toResponse(llave);
    }

    public List<LlaveResponse> findByNumeroHabitacion(String numeroHabitacion) {
        return llaveMapper.toResponseList(llaveRepository.findByNumeroHabitacion(numeroHabitacion));
    }

    public List<LlaveResponse> findByActiva(Boolean activa) {
        return llaveMapper.toResponseList(llaveRepository.findByActiva(activa));
    }

    public List<LlaveResponse> findByCodigoReserva(String codigoReserva) {
        return llaveMapper.toResponseList(llaveRepository.findByReservaCodigoReserva(codigoReserva));
    }

    public LlaveResponse create(LlaveRequest request) {
        validateCodigoLlaveDisponible(request.getCodigoLlave());

        ProjReserva reserva = getReservaOpcional(request.getCodigoReserva());
        Llave llave = llaveMapper.toEntity(request, reserva);
        llave.setActiva(request.getActiva() != null ? request.getActiva() : Boolean.TRUE);
        llave.setEmitidaEn(LocalDate.now());

        return llaveMapper.toResponse(llaveRepository.save(llave));
    }

    public LlaveResponse update(Long id, LlaveRequest request) {
        Llave llave = getLlave(id);

        llaveRepository.findByCodigoLlave(request.getCodigoLlave())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe una llave con codigo: " + request.getCodigoLlave());
                });

        ProjReserva reserva = getReservaOpcional(request.getCodigoReserva());
        llaveMapper.updateEntity(request, reserva, llave);
        llave.setActiva(request.getActiva() != null ? request.getActiva() : Boolean.TRUE);

        return llaveMapper.toResponse(llaveRepository.save(llave));
    }

    public LlaveResponse updateEstado(Long id, Boolean activa) {
        Llave llave = getLlave(id);
        llave.setActiva(activa);

        return llaveMapper.toResponse(llaveRepository.save(llave));
    }

    public void deleteById(Long id) {
        Llave llave = getLlave(id);
        llaveRepository.delete(llave);
    }

    private Llave getLlave(Long id) {
        return llaveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Llave no encontrada: " + id));
    }

    private ProjReserva getReservaOpcional(String codigoReserva) {
        if (codigoReserva == null || codigoReserva.isBlank()) {
            return null;
        }

        return reservaRepository.findById(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada: " + codigoReserva));
    }

    private void validateCodigoLlaveDisponible(String codigoLlave) {
        if (llaveRepository.existsByCodigoLlave(codigoLlave)) {
            throw new IllegalArgumentException("Ya existe una llave con codigo: " + codigoLlave);
        }
    }
}