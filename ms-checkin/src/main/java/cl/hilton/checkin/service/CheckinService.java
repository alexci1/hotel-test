package cl.hilton.checkin.service;

import cl.hilton.checkin.dto.CheckinRequest;
import cl.hilton.checkin.dto.CheckinResponse;
import cl.hilton.checkin.mapper.CheckinMapper;
import cl.hilton.checkin.model.Checkin;
import cl.hilton.checkin.model.ProjHuesped;
import cl.hilton.checkin.model.ProjReserva;
import cl.hilton.checkin.repository.CheckinRepository;
import cl.hilton.checkin.repository.ProjHuespedRepository;
import cl.hilton.checkin.repository.ProjReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckinService {

    private final CheckinRepository checkinRepository;
    private final ProjReservaRepository reservaRepository;
    private final ProjHuespedRepository huespedRepository;
    private final CheckinMapper checkinMapper;

    public CheckinService(
            CheckinRepository checkinRepository,
            ProjReservaRepository reservaRepository,
            ProjHuespedRepository huespedRepository,
            CheckinMapper checkinMapper
    ) {
        this.checkinRepository = checkinRepository;
        this.reservaRepository = reservaRepository;
        this.huespedRepository = huespedRepository;
        this.checkinMapper = checkinMapper;
    }

    public List<CheckinResponse> listar() {
        return checkinMapper.toResponseList(checkinRepository.findAll());
    }

    public CheckinResponse buscarPorId(Long id) {
        return checkinMapper.toResponse(obtenerCheckin(id));
    }

    public CheckinResponse buscarPorReserva(String codigoReserva) {
        Checkin checkin = checkinRepository.findByReservaCodigoReserva(codigoReserva)
                .orElseThrow(() -> new RuntimeException("Check-in no encontrado"));

        return checkinMapper.toResponse(checkin);
    }

    public List<CheckinResponse> buscarPorHuesped(String emailHuesped) {
        return checkinMapper.toResponseList(checkinRepository.findByHuespedEmail(emailHuesped));
    }

    public CheckinResponse crear(CheckinRequest request) {
        if (checkinRepository.findByReservaCodigoReserva(request.getCodigoReserva()).isPresent()) {
            throw new RuntimeException("Ya existe un check-in para esa reserva");
        }

        ProjReserva reserva = obtenerReserva(request.getCodigoReserva());
        ProjHuesped huesped = obtenerHuesped(request.getEmailHuesped());

        Checkin checkin = checkinMapper.toEntity(request, reserva, huesped);

        return checkinMapper.toResponse(checkinRepository.save(checkin));
    }

    public CheckinResponse actualizar(Long id, CheckinRequest request) {
        Checkin checkin = obtenerCheckin(id);
        ProjReserva reserva = obtenerReserva(request.getCodigoReserva());
        ProjHuesped huesped = obtenerHuesped(request.getEmailHuesped());

        checkinMapper.updateEntity(request, reserva, huesped, checkin);

        return checkinMapper.toResponse(checkinRepository.save(checkin));
    }

    public void eliminar(Long id) {
        Checkin checkin = obtenerCheckin(id);
        checkinRepository.delete(checkin);
    }

    private Checkin obtenerCheckin(Long id) {
        return checkinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Check-in no encontrado"));
    }

    private ProjReserva obtenerReserva(String codigoReserva) {
        return reservaRepository.findById(codigoReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    private ProjHuesped obtenerHuesped(String email) {
        return huespedRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));
    }
}
