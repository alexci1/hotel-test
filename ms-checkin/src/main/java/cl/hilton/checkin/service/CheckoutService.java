package cl.hilton.checkin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.checkin.dto.CheckoutRequest;
import cl.hilton.checkin.dto.CheckoutResponse;
import cl.hilton.checkin.mapper.CheckoutMapper;
import cl.hilton.checkin.model.Checkout;
import cl.hilton.checkin.model.ProjReserva;
import cl.hilton.checkin.repository.CheckoutRepository;
import cl.hilton.checkin.repository.ProjReservaRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final ProjReservaRepository reservaRepository;
    private final CheckoutMapper checkoutMapper;

    public List<CheckoutResponse> findAll() {
        return checkoutMapper.toResponseList(checkoutRepository.findAll());
    }

    public CheckoutResponse findById(Long id) {
        Checkout checkout = getCheckoutById(id);
        return checkoutMapper.toResponse(checkout);
    }

    public CheckoutResponse findByCodigoReserva(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        Checkout checkout = checkoutRepository.findByReservaCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Checkout no encontrado para reserva: " + codigo));

        return checkoutMapper.toResponse(checkout);
    }

    @Transactional
    public CheckoutResponse create(CheckoutRequest request) {
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");

        validarCheckoutUnico(codigoReserva);

        ProjReserva reserva = getReservaByCodigo(codigoReserva);
        Checkout checkout = checkoutMapper.toEntity(request, reserva);
        checkout.setFechaHora(LocalDate.now());

        Checkout checkoutGuardado = checkoutRepository.save(checkout);

        return checkoutMapper.toResponse(checkoutGuardado);
    }

    @Transactional
    public CheckoutResponse update(Long id, CheckoutRequest request) {
        Long checkoutId = validarId(id);
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");

        Checkout checkout = getCheckoutById(checkoutId);
        ProjReserva reserva = getReservaByCodigo(codigoReserva);

        checkoutRepository.findByReservaCodigoReserva(codigoReserva)
                .filter(existente -> !existente.getId().equals(checkoutId))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un checkout para la reserva: " + codigoReserva);
                });

        checkoutMapper.updateEntity(request, reserva, checkout);

        Checkout checkoutActualizado = checkoutRepository.save(checkout);

        return checkoutMapper.toResponse(checkoutActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long checkoutId = validarId(id);
        getCheckoutById(checkoutId);
        checkoutRepository.deleteById(checkoutId);
    }

    private Checkout getCheckoutById(Long id) {
        Long checkoutId = validarId(id);

        return checkoutRepository.findById(checkoutId)
                .orElseThrow(() -> new EntityNotFoundException("Checkout no encontrado con id: " + checkoutId));
    }

    private ProjReserva getReservaByCodigo(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        return reservaRepository.findById(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada: " + codigo));
    }

    private void validarCheckoutUnico(String codigoReserva) {
        if (checkoutRepository.findByReservaCodigoReserva(codigoReserva).isPresent()) {
            throw new IllegalArgumentException("Ya existe un checkout para la reserva: " + codigoReserva);
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
