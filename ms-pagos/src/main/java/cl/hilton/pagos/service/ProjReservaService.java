package cl.hilton.pagos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.pagos.client.ReservaClient;
import cl.hilton.pagos.dto.ProjReservaRequest;
import cl.hilton.pagos.dto.ProjReservaResponse;
import cl.hilton.pagos.mapper.ProjReservaMapper;
import cl.hilton.pagos.model.ProjReserva;
import cl.hilton.pagos.repository.ProjReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProjReservaService {

    private final ProjReservaRepository reservaRepository;
    private final ProjReservaMapper reservaMapper;
    private final ReservaClient reservaClient;

    public List<ProjReservaResponse> findAll() {
        return reservaMapper.toResponseList(reservaRepository.findAll());
    }

    public ProjReservaResponse findByCodigoReserva(String codigoReserva) {
        ProjReserva reserva = getReservaByCodigo(codigoReserva);
        return reservaMapper.toResponse(reserva);
    }

    public List<ProjReservaResponse> findByEmailHuesped(String emailHuesped) {
        String email = validarTexto(emailHuesped, "emailHuesped");
        return reservaMapper.toResponseList(reservaRepository.findByEmailHuesped(email));
    }

    public List<ProjReservaResponse> findByNumeroHabitacion(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        return reservaMapper.toResponseList(reservaRepository.findByNumeroHabitacion(numero));
    }

    public List<ProjReservaResponse> findByFechaEntrada(LocalDate fechaEntrada) {
        LocalDate fecha = validarFecha(fechaEntrada, "fechaEntrada");
        return reservaMapper.toResponseList(reservaRepository.findByFechaEntrada(fecha));
    }

    public List<ProjReservaResponse> findByFechaSalida(LocalDate fechaSalida) {
        LocalDate fecha = validarFecha(fechaSalida, "fechaSalida");
        return reservaMapper.toResponseList(reservaRepository.findByFechaSalida(fecha));
    }

    @Transactional
    public ProjReservaResponse create(ProjReservaRequest request) {
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");
        validarCodigoReservaUnico(codigoReserva);

        ProjReserva reserva = reservaMapper.toEntity(request);
        reserva.setActualizadoEn(LocalDate.now());

        ProjReserva reservaGuardada = reservaRepository.save(reserva);

        return reservaMapper.toResponse(reservaGuardada);
    }

    @Transactional
    public ProjReservaResponse update(String codigoReserva, ProjReservaRequest request) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");
        ProjReserva reserva = getReservaByCodigo(codigo);

        reservaMapper.updateEntity(request, reserva);
        reserva.setCodigoReserva(codigo);
        reserva.setActualizadoEn(LocalDate.now());

        ProjReserva reservaActualizada = reservaRepository.save(reserva);

        return reservaMapper.toResponse(reservaActualizada);
    }

    @Transactional
    public ProjReservaResponse sincronizarPorCodigoReserva(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        ProjReservaResponse externa = reservaClient.buscarPorCodigoReserva(codigo);
        ProjReserva reserva = reservaRepository.findByCodigoReserva(externa.getCodigoReserva())
                .orElseGet(ProjReserva::new);

        reserva.setCodigoReserva(externa.getCodigoReserva());
        reserva.setEmailHuesped(externa.getEmailHuesped());
        reserva.setNumeroHabitacion(externa.getNumeroHabitacion());
        reserva.setFechaEntrada(externa.getFechaEntrada());
        reserva.setFechaSalida(externa.getFechaSalida());
        reserva.setActualizadoEn(LocalDate.now());

        ProjReserva reservaGuardada = reservaRepository.save(reserva);

        return reservaMapper.toResponse(reservaGuardada);
    }

    @Transactional
    public void deleteByCodigoReserva(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");
        getReservaByCodigo(codigo);
        reservaRepository.deleteById(codigo);
    }

    private ProjReserva getReservaByCodigo(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        return reservaRepository.findByCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reserva proyectada no encontrada con codigo: " + codigo));
    }

    private void validarCodigoReservaUnico(String codigoReserva) {
        if (reservaRepository.existsByCodigoReserva(codigoReserva)) {
            throw new IllegalArgumentException("Ya existe una reserva proyectada con codigo: " + codigoReserva);
        }
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
