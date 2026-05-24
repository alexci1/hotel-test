package cl.hilton.checkin.mapper;

import cl.hilton.checkin.dto.CheckoutRequest;
import cl.hilton.checkin.dto.CheckoutResponse;
import cl.hilton.checkin.model.Checkout;
import cl.hilton.checkin.model.ProjReserva;
import org.springframework.stereotype.Component;

@Component
public class CheckoutMapper {

    public Checkout toEntity(CheckoutRequest request, ProjReserva reserva) {
        return Checkout.builder()
                .reserva(reserva)
                .fechaHora(request.getFechaHora())
                .realizadoPor(request.getRealizadoPor())
                .observaciones(request.getObservaciones())
                .build();
    }

    public CheckoutResponse toResponse(Checkout checkout) {
        return CheckoutResponse.builder()
                .id(checkout.getId())
                .codigoReserva(checkout.getReserva().getCodigoReserva())
                .fechaHora(checkout.getFechaHora())
                .realizadoPor(checkout.getRealizadoPor())
                .observaciones(checkout.getObservaciones())
                .build();
    }

    public void updateEntity(Checkout checkout, CheckoutRequest request, ProjReserva reserva) {
        checkout.setReserva(reserva);
        checkout.setFechaHora(request.getFechaHora());
        checkout.setRealizadoPor(request.getRealizadoPor());
        checkout.setObservaciones(request.getObservaciones());
    }
}