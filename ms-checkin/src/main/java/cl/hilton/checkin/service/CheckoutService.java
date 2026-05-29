package cl.hilton.checkin.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
        String codigo = Objects.requireNonNull(codigoReserva, "codigoReserva no puede ser null");

        Checkout checkout = checkoutRepository
                .findByReservaCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Checkout no encontrado para reserva: " + codigo));

        return checkoutMapper.toResponse(checkout);
    }

    public CheckoutResponse create(CheckoutRequest request) {
        String codigoReserva = Objects.requireNonNull(request.getCodigoReserva(), "codigoReserva no puede ser null");

        if (checkoutRepository.findByReservaCodigoReserva(codigoReserva).isPresent()) {
            throw new IllegalArgumentException("Ya existe un checkout para la reserva: " + codigoReserva);
        }

        ProjReserva reserva = getReserva(codigoReserva);
        Checkout checkout = checkoutMapper.toEntity(request, reserva);
        checkout.setFechaHora(LocalDate.now());

        Checkout saved = checkoutRepository.save(Objects.requireNonNull(checkout));
        return checkoutMapper.toResponse(saved);
    }

    public CheckoutResponse update(Long id, CheckoutRequest request) {
        Long checkoutId = Objects.requireNonNull(id, "id no puede ser null");
        String codigoReserva = Objects.requireNonNull(request.getCodigoReserva(), "codigoReserva no puede ser null");

        Checkout checkout = getCheckout(checkoutId);
        ProjReserva reserva = getReserva(codigoReserva);

        checkoutRepository.findByReservaCodigoReserva(codigoReserva)
                .filter(existente -> !existente.getId().equals(checkoutId))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un checkout para la reserva: " + codigoReserva);
                });

        checkoutMapper.updateEntity(request, reserva, checkout);

        Checkout saved = checkoutRepository.save(Objects.requireNonNull(checkout));
        return checkoutMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Checkout checkout = getCheckout(id);
        checkoutRepository.delete(Objects.requireNonNull(checkout));
    }

    private Checkout getCheckout(Long id) {
        Long checkoutId = Objects.requireNonNull(id, "id no puede ser null");

        return checkoutRepository.findById(checkoutId)
                .orElseThrow(() -> new EntityNotFoundException("Checkout no encontrado: " + checkoutId));
    }

    private ProjReserva getReserva(String codigoReserva) {
        String codigo = Objects.requireNonNull(codigoReserva, "codigoReserva no puede ser null");

        return reservaRepository.findById(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada: " + codigo));
    }
}