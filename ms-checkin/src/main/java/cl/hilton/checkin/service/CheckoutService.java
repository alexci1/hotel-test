package cl.hilton.checkin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.checkin.dto.CheckoutRequest;
import cl.hilton.checkin.dto.CheckoutResponse;
import cl.hilton.checkin.mapper.CheckoutMapper;
import cl.hilton.checkin.model.Checkout;
import cl.hilton.checkin.model.ProjReserva;
import cl.hilton.checkin.repository.CheckoutRepository;
import cl.hilton.checkin.repository.ProjReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final ProjReservaRepository reservaRepository;
    private final CheckoutMapper checkoutMapper;

    public List<CheckoutResponse> findAll() {
        return checkoutMapper.toResponseList(checkoutRepository.findAll());
    }

    public CheckoutResponse findById(Long id) {
        return checkoutMapper.toResponse(getCheckout(id));
    }

    public CheckoutResponse findByCodigoReserva(String codigoReserva) {
        Checkout checkout = checkoutRepository
                .findByReservaCodigoReserva(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Checkout no encontrado para reserva: " + codigoReserva));

        return checkoutMapper.toResponse(checkout);
    }

    public CheckoutResponse create(CheckoutRequest request) {
        if (checkoutRepository.findByReservaCodigoReserva(request.getCodigoReserva()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un checkout para la reserva: " + request.getCodigoReserva());
        }

        ProjReserva reserva = getReserva(request.getCodigoReserva());
        Checkout checkout = checkoutMapper.toEntity(request, reserva);
        checkout.setFechaHora(LocalDate.now());

        return checkoutMapper.toResponse(checkoutRepository.save(checkout));
    }

    public CheckoutResponse update(Long id, CheckoutRequest request) {
        Checkout checkout = getCheckout(id);
        ProjReserva reserva = getReserva(request.getCodigoReserva());

        checkoutRepository.findByReservaCodigoReserva(request.getCodigoReserva())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un checkout para la reserva: " + request.getCodigoReserva());
                });

        checkoutMapper.updateEntity(request, reserva, checkout);

        return checkoutMapper.toResponse(checkoutRepository.save(checkout));
    }

    public void deleteById(Long id) {
        Checkout checkout = getCheckout(id);
        checkoutRepository.delete(checkout);
    }

    private Checkout getCheckout(Long id) {
        return checkoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checkout no encontrado: " + id));
    }

    private ProjReserva getReserva(String codigoReserva) {
        return reservaRepository.findById(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada: " + codigoReserva));
    }
}