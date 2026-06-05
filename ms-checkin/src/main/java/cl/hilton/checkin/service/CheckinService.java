package cl.hilton.checkin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.checkin.dto.CheckinRequest;
import cl.hilton.checkin.dto.CheckinResponse;
import cl.hilton.checkin.mapper.CheckinMapper;
import cl.hilton.checkin.model.Checkin;
import cl.hilton.checkin.model.ProjHuesped;
import cl.hilton.checkin.model.ProjReserva;
import cl.hilton.checkin.repository.CheckinRepository;
import cl.hilton.checkin.repository.ProjHuespedRepository;
import cl.hilton.checkin.repository.ProjReservaRepository;
import cl.hilton.common.exception.EntityNotFoundException;
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
        Checkin checkin = getCheckinById(id);
        return checkinMapper.toResponse(checkin);
    }

    public CheckinResponse findByCodigoReserva(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        Checkin checkin = checkinRepository.findByReservaCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Check-in no encontrado para reserva: " + codigo));

        return checkinMapper.toResponse(checkin);
    }

    public List<CheckinResponse> findByEmailHuesped(String emailHuesped) {
        String email = validarTexto(emailHuesped, "emailHuesped");
        return checkinMapper.toResponseList(checkinRepository.findByHuespedEmail(email));
    }

    public List<CheckinResponse> findByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        return checkinMapper.toResponseList(checkinRepository.findByNumeroHabitacion(numero));
    }

    @Transactional
    public CheckinResponse create(CheckinRequest request) {
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");

        validarCheckinUnico(codigoReserva);

        ProjReserva reserva = getReservaByCodigo(codigoReserva);
        ProjHuesped huesped = getHuespedByEmail(emailHuesped);

        Checkin checkin = checkinMapper.toEntity(request, reserva, huesped);
        checkin.setFechaHora(LocalDate.now());

        Checkin checkinGuardado = checkinRepository.save(checkin);

        return checkinMapper.toResponse(checkinGuardado);
    }

    @Transactional
    public CheckinResponse update(Long id, CheckinRequest request) {
        Long checkinId = validarId(id);
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");

        Checkin checkin = getCheckinById(checkinId);
        ProjReserva reserva = getReservaByCodigo(codigoReserva);
        ProjHuesped huesped = getHuespedByEmail(emailHuesped);

        checkinRepository.findByReservaCodigoReserva(codigoReserva)
                .filter(existente -> !existente.getId().equals(checkinId))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un check-in para la reserva: " + codigoReserva);
                });

        checkinMapper.updateEntity(request, reserva, huesped, checkin);

        Checkin checkinActualizado = checkinRepository.save(checkin);

        return checkinMapper.toResponse(checkinActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long checkinId = validarId(id);
        getCheckinById(checkinId);
        checkinRepository.deleteById(checkinId);
    }

    private Checkin getCheckinById(Long id) {
        Long checkinId = validarId(id);

        return checkinRepository.findById(checkinId)
                .orElseThrow(() -> new EntityNotFoundException("Check-in no encontrado con id: " + checkinId));
    }

    private ProjReserva getReservaByCodigo(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        return reservaRepository.findById(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada: " + codigo));
    }

    private ProjHuesped getHuespedByEmail(String email) {
        String emailHuesped = validarTexto(email, "email");

        return huespedRepository.findById(emailHuesped)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado: " + emailHuesped));
    }

    private void validarCheckinUnico(String codigoReserva) {
        if (checkinRepository.findByReservaCodigoReserva(codigoReserva).isPresent()) {
            throw new IllegalArgumentException("Ya existe un check-in para la reserva: " + codigoReserva);
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
