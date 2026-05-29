package cl.hilton.checkin.service;

import cl.hilton.checkin.dto.CheckoutRequest;
import cl.hilton.checkin.dto.CheckoutResponse;
import cl.hilton.checkin.mapper.CheckoutMapper;
import cl.hilton.checkin.model.Checkout;
import cl.hilton.checkin.model.ProjReserva;
import cl.hilton.checkin.repository.CheckoutRepository;
import cl.hilton.checkin.repository.ProjReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final ProjReservaRepository reservaRepository;
    private final CheckoutMapper checkoutMapper;

    public CheckoutService(
            CheckoutRepository checkoutRepository,
            ProjReservaRepository reservaRepository,
            CheckoutMapper checkoutMapper
    ) {
        this.checkoutRepository = checkoutRepository;
        this.reservaRepository = reservaRepository;
        this.checkoutMapper = checkoutMapper;
    }

    public List<CheckoutResponse> listar() {
        return checkoutMapper.toResponseList(checkoutRepository.findAll());
    }

    public CheckoutResponse buscarPorId(Long id) {
        return checkoutMapper.toResponse(obtenerCheckout(id));
    }

    public CheckoutResponse buscarPorReserva(String codigoReserva) {
        Checkout checkout = checkoutRepository
                .findByReservaCodigoReserva(codigoReserva)
                .orElseThrow(() -> new RuntimeException("Checkout no encontrado"));

        return checkoutMapper.toResponse(checkout);
    }

    public CheckoutResponse crear(CheckoutRequest request) {
        if (checkoutRepository.findByReservaCodigoReserva(request.getCodigoReserva()).isPresent()) {
            throw new RuntimeException("Ya existe un checkout para esa reserva");
        }

        ProjReserva reserva = obtenerReserva(request.getCodigoReserva());
        Checkout checkout = checkoutMapper.toEntity(request, reserva);

        return checkoutMapper.toResponse(checkoutRepository.save(checkout));
    }

    public CheckoutResponse actualizar(Long id, CheckoutRequest request) {
        Checkout checkout = obtenerCheckout(id);
        ProjReserva reserva = obtenerReserva(request.getCodigoReserva());

        checkoutMapper.updateEntity(request, reserva, checkout);

        return checkoutMapper.toResponse(checkoutRepository.save(checkout));
    }

    public void eliminar(Long id) {
        Checkout checkout = obtenerCheckout(id);
        checkoutRepository.delete(checkout);
    }

    private Checkout obtenerCheckout(Long id) {
        return checkoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checkout no encontrado"));
    }

    private ProjReserva obtenerReserva(String codigoReserva) {
        return reservaRepository.findById(codigoReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }
}
