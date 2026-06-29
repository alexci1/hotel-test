package cl.hilton.checkin.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cl.hilton.checkin.dto.CheckoutRequest;
import cl.hilton.checkin.dto.CheckoutResponse;
import cl.hilton.checkin.model.Checkout;
import cl.hilton.checkin.model.ProjReserva;

@Component
public class CheckoutMapper {

    public Checkout toEntity(CheckoutRequest request, ProjReserva reserva) {
        if (request == null && reserva == null) {
            return null;
        }

        Checkout checkout = new Checkout();
        checkout.setReserva(reserva);

        if (request != null) {
            checkout.setRealizadoPor(request.getRealizadoPor());
            checkout.setObservaciones(request.getObservaciones());
        }

        return checkout;
    }

    public CheckoutResponse toResponse(Checkout checkout) {
        if (checkout == null) {
            return null;
        }

        CheckoutResponse response = new CheckoutResponse();
        response.setId(checkout.getId());
        response.setFechaHora(checkout.getFechaHora());
        response.setRealizadoPor(checkout.getRealizadoPor());
        response.setObservaciones(checkout.getObservaciones());

        if (checkout.getReserva() != null) {
            response.setCodigoReserva(checkout.getReserva().getCodigoReserva());
        }

        return response;
    }

    public List<CheckoutResponse> toResponseList(List<Checkout> checkouts) {
        if (checkouts == null) {
            return null;
        }

        return checkouts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(CheckoutRequest request, ProjReserva reserva, Checkout checkout) {
        if (checkout == null) {
            return;
        }

        checkout.setReserva(reserva);

        if (request != null) {
            checkout.setRealizadoPor(request.getRealizadoPor());
            checkout.setObservaciones(request.getObservaciones());
        }
    }
}
