package cl.hilton.checkin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.checkin.dto.CheckinRequest;
import cl.hilton.checkin.dto.CheckinResponse;
import cl.hilton.checkin.mapper.CheckinMapper;
import cl.hilton.checkin.model.Checkin;
import cl.hilton.checkin.model.ProjHuesped;
import cl.hilton.checkin.model.ProjReserva;
import cl.hilton.checkin.repository.CheckinRepository;
import cl.hilton.checkin.repository.ProjHuespedRepository;
import cl.hilton.checkin.repository.ProjReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckinService {

    private final CheckinRepository checkinRepository;
    private final ProjReservaRepository reservaRepository;
    private final ProjHuespedRepository huespedRepository;
    private final CheckinMapper checkinMapper;

    public List<CheckinResponse> findAll() {
        return checkinMapper.toResponseList(checkinRepository.findAll());
    }

    public CheckinResponse findById(Long id) {
        return checkinMapper.toResponse(getCheckin(id));
    }

    public CheckinResponse findByCodigoReserva(String codigoReserva) {
        Checkin checkin = checkinRepository.findByReservaCodigoReserva(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Check-in no encontrado para reserva: " + codigoReserva));

        return checkinMapper.toResponse(checkin);
    }

    public List<CheckinResponse> findByEmailHuesped(String emailHuesped) {
        return checkinMapper.toResponseList(checkinRepository.findByHuespedEmail(emailHuesped));
    }

    public List<CheckinResponse> findByNumeroHabitacion(String numeroHabitacion) {
        return checkinMapper.toResponseList(checkinRepository.findByNumeroHabitacion(numeroHabitacion));
    }

    public CheckinResponse create(CheckinRequest request) {
        if (checkinRepository.findByReservaCodigoReserva(request.getCodigoReserva()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un check-in para la reserva: " + request.getCodigoReserva());
        }

        ProjReserva reserva = getReserva(request.getCodigoReserva());
        ProjHuesped huesped = getHuesped(request.getEmailHuesped());

        Checkin checkin = checkinMapper.toEntity(request, reserva, huesped);
        checkin.setFechaHora(LocalDate.now());

        Checkin saved = checkinRepository.save(checkin);
        return checkinMapper.toResponse(saved);
    }

    public CheckinResponse update(Long id, CheckinRequest request) {
        Checkin checkin = getCheckin(id);
        ProjReserva reserva = getReserva(request.getCodigoReserva());
        ProjHuesped huesped = getHuesped(request.getEmailHuesped());

        checkinRepository.findByReservaCodigoReserva(request.getCodigoReserva())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un check-in para la reserva: " + request.getCodigoReserva());
                });

        checkinMapper.updateEntity(request, reserva, huesped, checkin);

        Checkin saved = checkinRepository.save(checkin);
        return checkinMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Checkin checkin = getCheckin(id);
        checkinRepository.delete(checkin);
    }

    private Checkin getCheckin(Long id) {
        return checkinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Check-in no encontrado: " + id));
    }

    private ProjReserva getReserva(String codigoReserva) {
        return reservaRepository.findById(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada: " + codigoReserva));
    }

    private ProjHuesped getHuesped(String email) {
        return huespedRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado: " + email));
    }
}