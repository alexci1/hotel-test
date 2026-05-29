package cl.hilton.reservas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.reservas.dto.CancelacionRequest;
import cl.hilton.reservas.dto.CancelacionResponse;
import cl.hilton.reservas.mapper.CancelacionMapper;
import cl.hilton.reservas.model.Cancelacion;
import cl.hilton.reservas.model.Reserva;
import cl.hilton.reservas.repository.CancelacionRepository;
import cl.hilton.reservas.repository.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CancelacionService {

    private final CancelacionRepository cancelacionRepository;
    private final ReservaRepository reservaRepository;
    private final CancelacionMapper cancelacionMapper;

    public List<CancelacionResponse> findAll() {
        return cancelacionMapper.toResponseList(cancelacionRepository.findAll());
    }

    public CancelacionResponse findById(Long id) {
        Cancelacion cancelacion = getCancelacionById(id);
        return cancelacionMapper.toResponse(cancelacion);
    }

    public CancelacionResponse findByCodigoReserva(String codigoReserva) {
        Cancelacion cancelacion = cancelacionRepository.findByReservaCodigoReserva(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Cancelacion no encontrada para reserva: " + codigoReserva));

        return cancelacionMapper.toResponse(cancelacion);
    }

    public List<CancelacionResponse> findByCanceladoEn(LocalDate canceladoEn) {
        return cancelacionMapper.toResponseList(cancelacionRepository.findByCanceladoEn(canceladoEn));
    }

    public CancelacionResponse create(CancelacionRequest request) {
        if (cancelacionRepository.existsByReservaCodigoReserva(request.getCodigoReserva())) {
            throw new IllegalArgumentException("Ya existe una cancelacion para la reserva: " + request.getCodigoReserva());
        }

        Reserva reserva = reservaRepository.findByCodigoReserva(request.getCodigoReserva())
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con codigo: " + request.getCodigoReserva()));

        Cancelacion cancelacion = cancelacionMapper.toEntity(request);
        cancelacion.setReserva(reserva);
        cancelacion.setCanceladoEn(LocalDate.now());
        cancelacion.setPenalidadUsd(request.getPenalidadUsd() != null ? request.getPenalidadUsd() : 0);

        reserva.setEstado("CANCELADA");
        reservaRepository.save(reserva);

        Cancelacion cancelacionGuardada = cancelacionRepository.save(cancelacion);

        return cancelacionMapper.toResponse(cancelacionGuardada);
    }

    public CancelacionResponse update(Long id, CancelacionRequest request) {
        Cancelacion cancelacion = getCancelacionById(id);

        if (!cancelacion.getReserva().getCodigoReserva().equalsIgnoreCase(request.getCodigoReserva())
                && cancelacionRepository.existsByReservaCodigoReserva(request.getCodigoReserva())) {
            throw new IllegalArgumentException("Ya existe una cancelacion para la reserva: " + request.getCodigoReserva());
        }

        Reserva reserva = reservaRepository.findByCodigoReserva(request.getCodigoReserva())
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con codigo: " + request.getCodigoReserva()));

        cancelacionMapper.updateEntity(request, cancelacion);
        cancelacion.setReserva(reserva);
        cancelacion.setPenalidadUsd(request.getPenalidadUsd() != null ? request.getPenalidadUsd() : cancelacion.getPenalidadUsd());

        Cancelacion cancelacionActualizada = cancelacionRepository.save(cancelacion);

        return cancelacionMapper.toResponse(cancelacionActualizada);
    }

    public void deleteById(Long id) {
        Cancelacion cancelacion = getCancelacionById(id);
        cancelacionRepository.delete(cancelacion);
    }

    private Cancelacion getCancelacionById(Long id) {
        return cancelacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cancelacion no encontrada con id: " + id));
    }
}
