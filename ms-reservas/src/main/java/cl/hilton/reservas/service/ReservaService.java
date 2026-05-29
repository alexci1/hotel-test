package cl.hilton.reservas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.reservas.dto.ReservaRequest;
import cl.hilton.reservas.dto.ReservaResponse;
import cl.hilton.reservas.mapper.ReservaMapper;
import cl.hilton.reservas.model.ProjHabitacion;
import cl.hilton.reservas.model.ProjHuesped;
import cl.hilton.reservas.model.Reserva;
import cl.hilton.reservas.repository.ProjHabitacionRepository;
import cl.hilton.reservas.repository.ProjHuespedRepository;
import cl.hilton.reservas.repository.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ProjHuespedRepository huespedRepository;
    private final ProjHabitacionRepository habitacionRepository;
    private final ReservaMapper reservaMapper;

    public List<ReservaResponse> findAll() {
        return reservaMapper.toResponseList(reservaRepository.findAll());
    }

    public ReservaResponse findById(Long id) {
        Reserva reserva = getReservaById(id);
        return reservaMapper.toResponse(reserva);
    }

    public ReservaResponse findByCodigoReserva(String codigoReserva) {
        Reserva reserva = reservaRepository.findByCodigoReserva(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con codigo: " + codigoReserva));

        return reservaMapper.toResponse(reserva);
    }

    public List<ReservaResponse> findByEmailHuesped(String emailHuesped) {
        return reservaMapper.toResponseList(reservaRepository.findByHuespedEmail(emailHuesped));
    }

    public List<ReservaResponse> findByNumeroHabitacion(String numeroHabitacion) {
        return reservaMapper.toResponseList(reservaRepository.findByHabitacionNumeroHabitacion(numeroHabitacion));
    }

    public List<ReservaResponse> findByEstado(String estado) {
        return reservaMapper.toResponseList(reservaRepository.findByEstado(estado));
    }

    public List<ReservaResponse> findByFechaEntrada(LocalDate fechaEntrada) {
        return reservaMapper.toResponseList(reservaRepository.findByFechaEntrada(fechaEntrada));
    }

    public List<ReservaResponse> findByFechaSalida(LocalDate fechaSalida) {
        return reservaMapper.toResponseList(reservaRepository.findByFechaSalida(fechaSalida));
    }

    public List<ReservaResponse> findByRangoEntrada(LocalDate desde, LocalDate hasta) {
        return reservaMapper.toResponseList(reservaRepository.findByFechaEntradaBetween(desde, hasta));
    }

    public ReservaResponse create(ReservaRequest request) {
        validarCodigoUnico(request.getCodigoReserva());
        validarFechas(request.getFechaEntrada(), request.getFechaSalida());

        ProjHuesped huesped = huespedRepository.findByEmail(request.getEmailHuesped())
                .orElseThrow(() -> new EntityNotFoundException("Huesped proyectado no encontrado: " + request.getEmailHuesped()));

        ProjHabitacion habitacion = habitacionRepository.findByNumeroHabitacion(request.getNumeroHabitacion())
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada: " + request.getNumeroHabitacion()));

        Reserva reserva = reservaMapper.toEntity(request);
        reserva.setHuesped(huesped);
        reserva.setHabitacion(habitacion);
        reserva.setEstado(request.getEstado() != null ? request.getEstado() : "PENDIENTE");
        reserva.setCreadoEn(LocalDate.now());

        Reserva reservaGuardada = reservaRepository.save(reserva);

        return reservaMapper.toResponse(reservaGuardada);
    }

    public ReservaResponse update(Long id, ReservaRequest request) {
        Reserva reserva = getReservaById(id);
        String estadoActual = reserva.getEstado();

        if (!reserva.getCodigoReserva().equalsIgnoreCase(request.getCodigoReserva())) {
            validarCodigoUnico(request.getCodigoReserva());
        }

        validarFechas(request.getFechaEntrada(), request.getFechaSalida());

        ProjHuesped huesped = huespedRepository.findByEmail(request.getEmailHuesped())
                .orElseThrow(() -> new EntityNotFoundException("Huesped proyectado no encontrado: " + request.getEmailHuesped()));

        ProjHabitacion habitacion = habitacionRepository.findByNumeroHabitacion(request.getNumeroHabitacion())
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada: " + request.getNumeroHabitacion()));

        reservaMapper.updateEntity(request, reserva);
        reserva.setHuesped(huesped);
        reserva.setHabitacion(habitacion);
        reserva.setEstado(request.getEstado() != null ? request.getEstado() : estadoActual);

        Reserva reservaActualizada = reservaRepository.save(reserva);

        return reservaMapper.toResponse(reservaActualizada);
    }

    public void deleteById(Long id) {
        Reserva reserva = getReservaById(id);
        reservaRepository.delete(reserva);
    }

    private Reserva getReservaById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con id: " + id));
    }

    private void validarCodigoUnico(String codigoReserva) {
        if (reservaRepository.existsByCodigoReserva(codigoReserva)) {
            throw new IllegalArgumentException("Ya existe una reserva con codigo: " + codigoReserva);
        }
    }

    private void validarFechas(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (!fechaSalida.isAfter(fechaEntrada)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada");
        }
    }
}
