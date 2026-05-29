package cl.hilton.checkin.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
        String codigo = Objects.requireNonNull(codigoReserva, "codigoReserva no puede ser null");

        Checkin checkin = checkinRepository.findByReservaCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Check-in no encontrado para reserva: " + codigo));

        return checkinMapper.toResponse(checkin);
    }

    public List<CheckinResponse> findByEmailHuesped(String emailHuesped) {
        String email = Objects.requireNonNull(emailHuesped, "emailHuesped no puede ser null");
        return checkinMapper.toResponseList(checkinRepository.findByHuespedEmail(email));
    }

    public List<CheckinResponse> findByNumeroHabitacion(String numeroHabitacion) {
        String numero = Objects.requireNonNull(numeroHabitacion, "numeroHabitacion no puede ser null");
        return checkinMapper.toResponseList(checkinRepository.findByNumeroHabitacion(numero));
    }

    public CheckinResponse create(CheckinRequest request) {
        String codigoReserva = Objects.requireNonNull(request.getCodigoReserva(), "codigoReserva no puede ser null");
        String emailHuesped = Objects.requireNonNull(request.getEmailHuesped(), "emailHuesped no puede ser null");

        if (checkinRepository.findByReservaCodigoReserva(codigoReserva).isPresent()) {
            throw new IllegalArgumentException("Ya existe un check-in para la reserva: " + codigoReserva);
        }

        ProjReserva reserva = getReserva(codigoReserva);
        ProjHuesped huesped = getHuesped(emailHuesped);

        Checkin checkin = checkinMapper.toEntity(request, reserva, huesped);
        checkin.setFechaHora(LocalDate.now());

        Checkin saved = checkinRepository.save(Objects.requireNonNull(checkin));
        return checkinMapper.toResponse(saved);
    }

    public CheckinResponse update(Long id, CheckinRequest request) {
        Long checkinId = Objects.requireNonNull(id, "id no puede ser null");
        String codigoReserva = Objects.requireNonNull(request.getCodigoReserva(), "codigoReserva no puede ser null");
        String emailHuesped = Objects.requireNonNull(request.getEmailHuesped(), "emailHuesped no puede ser null");

        Checkin checkin = getCheckin(checkinId);
        ProjReserva reserva = getReserva(codigoReserva);
        ProjHuesped huesped = getHuesped(emailHuesped);

        checkinRepository.findByReservaCodigoReserva(codigoReserva)
                .filter(existente -> !existente.getId().equals(checkinId))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un check-in para la reserva: " + codigoReserva);
                });

        checkinMapper.updateEntity(request, reserva, huesped, checkin);

        Checkin saved = checkinRepository.save(Objects.requireNonNull(checkin));
        return checkinMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Checkin checkin = getCheckin(id);
        checkinRepository.delete(Objects.requireNonNull(checkin));
    }

    private Checkin getCheckin(Long id) {
        Long checkinId = Objects.requireNonNull(id, "id no puede ser null");

        return checkinRepository.findById(checkinId)
                .orElseThrow(() -> new EntityNotFoundException("Check-in no encontrado: " + checkinId));
    }

    private ProjReserva getReserva(String codigoReserva) {
        String codigo = Objects.requireNonNull(codigoReserva, "codigoReserva no puede ser null");

        return reservaRepository.findById(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada: " + codigo));
    }

    private ProjHuesped getHuesped(String email) {
        String emailHuesped = Objects.requireNonNull(email, "email no puede ser null");

        return huespedRepository.findById(emailHuesped)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado: " + emailHuesped));
    }
}