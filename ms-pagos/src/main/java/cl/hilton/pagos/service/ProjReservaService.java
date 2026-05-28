package cl.hilton.pagos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

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
        return reservaMapper.toResponseList(reservaRepository.findByEmailHuesped(emailHuesped));
    }

    public List<ProjReservaResponse> findByNumeroHabitacion(String numeroHabitacion) {
        return reservaMapper.toResponseList(reservaRepository.findByNumeroHabitacion(numeroHabitacion));
    }

    public List<ProjReservaResponse> findByFechaEntrada(LocalDate fechaEntrada) {
        return reservaMapper.toResponseList(reservaRepository.findByFechaEntrada(fechaEntrada));
    }

    public List<ProjReservaResponse> findByFechaSalida(LocalDate fechaSalida) {
        return reservaMapper.toResponseList(reservaRepository.findByFechaSalida(fechaSalida));
    }

    public ProjReservaResponse create(ProjReservaRequest request) {
        validarCodigoReservaUnico(request.getCodigoReserva());

        ProjReserva reserva = reservaMapper.toEntity(request);
        reserva.setActualizadoEn(LocalDate.now());

        ProjReserva reservaGuardada = reservaRepository.save(reserva);

        return reservaMapper.toResponse(reservaGuardada);
    }

    public ProjReservaResponse update(String codigoReserva, ProjReservaRequest request) {
        ProjReserva reserva = getReservaByCodigo(codigoReserva);

        reservaMapper.updateEntity(request, reserva);
        reserva.setActualizadoEn(LocalDate.now());

        ProjReserva reservaActualizada = reservaRepository.save(reserva);

        return reservaMapper.toResponse(reservaActualizada);
    }

    public ProjReservaResponse sincronizarPorCodigoReserva(String codigoReserva) {
        ProjReservaResponse externa = reservaClient.buscarPorCodigoReserva(codigoReserva);
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

    public void deleteByCodigoReserva(String codigoReserva) {
        ProjReserva reserva = getReservaByCodigo(codigoReserva);
        reservaRepository.delete(reserva);
    }

    private ProjReserva getReservaByCodigo(String codigoReserva) {
        return reservaRepository.findByCodigoReserva(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Reserva proyectada no encontrada con codigo: " + codigoReserva));
    }

    private void validarCodigoReservaUnico(String codigoReserva) {
        if (reservaRepository.existsByCodigoReserva(codigoReserva)) {
            throw new IllegalArgumentException("Ya existe una reserva proyectada con codigo: " + codigoReserva);
        }
    }
}
