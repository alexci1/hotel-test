package cl.hilton.reservas.mapper;

import cl.hilton.reservas.dto.CancelacionRequest;
import cl.hilton.reservas.dto.CancelacionResponse;
import cl.hilton.reservas.model.Cancelacion;
import cl.hilton.reservas.model.Reserva;

public class CancelacionMapper {

    public static Cancelacion toEntity(
            CancelacionRequest request,
            Reserva reserva
    ) {

        return Cancelacion.builder()
                .reserva(reserva)
                .motivo(request.getMotivo())
                .canceladoPor(request.getCanceladoPor())
                .penalidadUsd(request.getPenalidadUsd())
                .build();
    }

    public static CancelacionResponse toResponse(Cancelacion cancelacion) {

        CancelacionResponse response = new CancelacionResponse();

        response.setId(cancelacion.getId());
        response.setCodigoReserva(
                cancelacion.getReserva().getCodigoReserva()
        );
        response.setMotivo(cancelacion.getMotivo());
        response.setCanceladoPor(cancelacion.getCanceladoPor());
        response.setCanceladoEn(cancelacion.getCanceladoEn());
        response.setPenalidadUsd(cancelacion.getPenalidadUsd());

        return response;
    }
}