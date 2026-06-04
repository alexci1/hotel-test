package cl.hilton.reservas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@SuppressWarnings("null")
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
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        Cancelacion cancelacion = cancelacionRepository.findByReservaCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Cancelacion no encontrada para reserva: " + codigo));

        return cancelacionMapper.toResponse(cancelacion);
    }

    public List<CancelacionResponse> findByCanceladoEn(LocalDate canceladoEn) {
        LocalDate fecha = validarFecha(canceladoEn, "canceladoEn");
        return cancelacionMapper.toResponseList(cancelacionRepository.findByCanceladoEn(fecha));
    }

    @Transactional
    public CancelacionResponse create(CancelacionRequest request) {
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");

        if (cancelacionRepository.existsByReservaCodigoReserva(codigoReserva)) {
            throw new IllegalArgumentException("Ya existe una cancelacion para la reserva: " + codigoReserva);
        }

        Reserva reserva = getReservaByCodigo(codigoReserva);

        Cancelacion cancelacion = cancelacionMapper.toEntity(request);
        cancelacion.setReserva(reserva);
        cancelacion.setCanceladoEn(LocalDate.now());
        cancelacion.setPenalidadUsd(request.getPenalidadUsd() != null ? request.getPenalidadUsd() : 0);

        reserva.setEstado("CANCELADA");
        reservaRepository.save(reserva);

        Cancelacion cancelacionGuardada = cancelacionRepository.save(cancelacion);

        return cancelacionMapper.toResponse(cancelacionGuardada);
    }

    @Transactional
    public CancelacionResponse update(Long id, CancelacionRequest request) {
        Long cancelacionId = validarId(id);
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");

        Cancelacion cancelacion = getCancelacionById(cancelacionId);
        Integer penalidadActual = cancelacion.getPenalidadUsd();

        if (!cancelacion.getReserva().getCodigoReserva().equalsIgnoreCase(codigoReserva)
                && cancelacionRepository.existsByReservaCodigoReserva(codigoReserva)) {
            throw new IllegalArgumentException("Ya existe una cancelacion para la reserva: " + codigoReserva);
        }

        Reserva reserva = getReservaByCodigo(codigoReserva);

        cancelacionMapper.updateEntity(request, cancelacion);
        cancelacion.setReserva(reserva);
        cancelacion.setPenalidadUsd(request.getPenalidadUsd() != null ? request.getPenalidadUsd() : penalidadActual);

        Cancelacion cancelacionActualizada = cancelacionRepository.save(cancelacion);

        return cancelacionMapper.toResponse(cancelacionActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long cancelacionId = validarId(id);
        getCancelacionById(cancelacionId);
        cancelacionRepository.deleteById(cancelacionId);
    }

    private Cancelacion getCancelacionById(Long id) {
        Long cancelacionId = validarId(id);

        return cancelacionRepository.findById(cancelacionId)
                .orElseThrow(() -> new EntityNotFoundException("Cancelacion no encontrada con id: " + cancelacionId));
    }

    private Reserva getReservaByCodigo(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        return reservaRepository.findByCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con codigo: " + codigo));
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
