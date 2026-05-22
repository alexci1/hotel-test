package cl.hilton.checkin.service;

import cl.hilton.checkin.dto.LlaveRequest;
import cl.hilton.checkin.dto.LlaveResponse;
import cl.hilton.checkin.mapper.LlaveMapper;
import cl.hilton.checkin.model.Llave;
import cl.hilton.checkin.model.ProjReserva;
import cl.hilton.checkin.repository.LlaveRepository;
import cl.hilton.checkin.repository.ProjReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LlaveService {

    private final LlaveRepository llaveRepository;
    private final ProjReservaRepository reservaRepository;
    private final LlaveMapper llaveMapper;

    public LlaveService(
            LlaveRepository llaveRepository,
            ProjReservaRepository reservaRepository,
            LlaveMapper llaveMapper
    ) {
        this.llaveRepository = llaveRepository;
        this.reservaRepository = reservaRepository;
        this.llaveMapper = llaveMapper;
    }

    public List<LlaveResponse> listar() {
        return llaveRepository.findAll().stream()
                .map(llaveMapper::toResponse)
                .toList();
    }

    public LlaveResponse buscarPorId(Long id) {
        return llaveMapper.toResponse(obtenerLlave(id));
    }

    public LlaveResponse buscarPorCodigoLlave(String codigoLlave) {
        Llave llave = llaveRepository.findByCodigoLlave(codigoLlave)
                .orElseThrow(() -> new RuntimeException("Llave no encontrada"));

        return llaveMapper.toResponse(llave);
    }

    public List<LlaveResponse> buscarPorHabitacion(String numeroHabitacion) {
        return llaveRepository.findByNumeroHabitacion(numeroHabitacion).stream()
                .map(llaveMapper::toResponse)
                .toList();
    }

    public List<LlaveResponse> buscarPorActiva(Boolean activa) {
        return llaveRepository.findByActiva(activa).stream()
                .map(llaveMapper::toResponse)
                .toList();
    }

    public LlaveResponse crear(LlaveRequest request) {
        if (llaveRepository.existsByCodigoLlave(request.getCodigoLlave())) {
            throw new RuntimeException("Ya existe una llave con ese código");
        }

        ProjReserva reserva = obtenerReservaOpcional(request.getCodigoReserva());
        Llave llave = llaveMapper.toEntity(request, reserva);

        return llaveMapper.toResponse(llaveRepository.save(llave));
    }

    public LlaveResponse actualizar(Long id, LlaveRequest request) {
        Llave llave = obtenerLlave(id);
        ProjReserva reserva = obtenerReservaOpcional(request.getCodigoReserva());

        llaveMapper.updateEntity(llave, request, reserva);

        return llaveMapper.toResponse(llaveRepository.save(llave));
    }

    public LlaveResponse cambiarEstado(Long id, Boolean activa) {
        Llave llave = obtenerLlave(id);
        llave.setActiva(activa);

        return llaveMapper.toResponse(llaveRepository.save(llave));
    }

    public void eliminar(Long id) {
        Llave llave = obtenerLlave(id);
        llaveRepository.delete(llave);
    }

    private Llave obtenerLlave(Long id) {
        return llaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Llave no encontrada"));
    }

    private ProjReserva obtenerReservaOpcional(String codigoReserva) {
        if (codigoReserva == null || codigoReserva.isBlank()) {
            return null;
        }

        return reservaRepository.findById(codigoReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }
}