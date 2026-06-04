package cl.hilton.reservas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@SuppressWarnings("null")
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
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        Reserva reserva = reservaRepository.findByCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con codigo: " + codigo));

        return reservaMapper.toResponse(reserva);
    }

    public List<ReservaResponse> findByEmailHuesped(String emailHuesped) {
        String email = validarTexto(emailHuesped, "emailHuesped");
        return reservaMapper.toResponseList(reservaRepository.findByHuespedEmail(email));
    }

    public List<ReservaResponse> findByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        return reservaMapper.toResponseList(reservaRepository.findByHabitacionNumeroHabitacion(numero));
    }

    public List<ReservaResponse> findByEstado(String estado) {
        String estadoValido = validarTexto(estado, "estado");
        return reservaMapper.toResponseList(reservaRepository.findByEstado(estadoValido));
    }

    public List<ReservaResponse> findByFechaEntrada(LocalDate fechaEntrada) {
        LocalDate fecha = validarFecha(fechaEntrada, "fechaEntrada");
        return reservaMapper.toResponseList(reservaRepository.findByFechaEntrada(fecha));
    }

    public List<ReservaResponse> findByFechaSalida(LocalDate fechaSalida) {
        LocalDate fecha = validarFecha(fechaSalida, "fechaSalida");
        return reservaMapper.toResponseList(reservaRepository.findByFechaSalida(fecha));
    }

    public List<ReservaResponse> findByRangoEntrada(LocalDate desde, LocalDate hasta) {
        LocalDate fechaDesde = validarFecha(desde, "desde");
        LocalDate fechaHasta = validarFecha(hasta, "hasta");
        validarRangoFechas(fechaDesde, fechaHasta);

        return reservaMapper.toResponseList(reservaRepository.findByFechaEntradaBetween(fechaDesde, fechaHasta));
    }

    @Transactional
    public ReservaResponse create(ReservaRequest request) {
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");
        LocalDate fechaEntrada = validarFecha(request.getFechaEntrada(), "fechaEntrada");
        LocalDate fechaSalida = validarFecha(request.getFechaSalida(), "fechaSalida");

        validarCodigoUnico(codigoReserva);
        validarFechas(fechaEntrada, fechaSalida);

        ProjHuesped huesped = getHuespedByEmail(emailHuesped);
        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);

        Reserva reserva = reservaMapper.toEntity(request);
        reserva.setHuesped(huesped);
        reserva.setHabitacion(habitacion);
        reserva.setEstado(request.getEstado() != null ? request.getEstado() : "PENDIENTE");
        reserva.setCreadoEn(LocalDate.now());

        Reserva reservaGuardada = reservaRepository.save(reserva);

        return reservaMapper.toResponse(reservaGuardada);
    }

    @Transactional
    public ReservaResponse update(Long id, ReservaRequest request) {
        Long reservaId = validarId(id);
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");
        LocalDate fechaEntrada = validarFecha(request.getFechaEntrada(), "fechaEntrada");
        LocalDate fechaSalida = validarFecha(request.getFechaSalida(), "fechaSalida");

        Reserva reserva = getReservaById(reservaId);
        String estadoActual = reserva.getEstado();

        if (!reserva.getCodigoReserva().equalsIgnoreCase(codigoReserva)) {
            validarCodigoUnico(codigoReserva);
        }

        validarFechas(fechaEntrada, fechaSalida);

        ProjHuesped huesped = getHuespedByEmail(emailHuesped);
        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);

        reservaMapper.updateEntity(request, reserva);
        reserva.setHuesped(huesped);
        reserva.setHabitacion(habitacion);
        reserva.setEstado(request.getEstado() != null ? request.getEstado() : estadoActual);

        Reserva reservaActualizada = reservaRepository.save(reserva);

        return reservaMapper.toResponse(reservaActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long reservaId = validarId(id);
        getReservaById(reservaId);
        reservaRepository.deleteById(reservaId);
    }

    private Reserva getReservaById(Long id) {
        Long reservaId = validarId(id);

        return reservaRepository.findById(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con id: " + reservaId));
    }

    private ProjHuesped getHuespedByEmail(String emailHuesped) {
        String email = validarTexto(emailHuesped, "emailHuesped");

        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped proyectado no encontrado: " + email));
    }

    private ProjHabitacion getHabitacionByNumero(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        return habitacionRepository.findByNumeroHabitacion(numero)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada: " + numero));
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

    private void validarRangoFechas(LocalDate desde, LocalDate hasta) {
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a la fecha hasta");
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private LocalDate validarFecha(LocalDate valor, String campo) {
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
