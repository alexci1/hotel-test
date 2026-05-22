package cl.hilton.reservas.mapper;

import cl.hilton.reservas.dto.ReservaRequest;
import cl.hilton.reservas.dto.ReservaResponse;
import cl.hilton.reservas.model.ProjHabitacion;
import cl.hilton.reservas.model.ProjHuesped;
import cl.hilton.reservas.model.Reserva;

public class ReservaMapper {

    public static Reserva toEntity(
            ReservaRequest request,
            ProjHuesped huesped,
            ProjHabitacion habitacion
    ) {

        return Reserva.builder()
                .codigoReserva(request.getCodigoReserva())
                .huesped(huesped)
                .habitacion(habitacion)
                .fechaEntrada(request.getFechaEntrada())
                .fechaSalida(request.getFechaSalida())
                .estado(request.getEstado())
                .build();
    }

    public static ReservaResponse toResponse(Reserva reserva) {

        ReservaResponse response = new ReservaResponse();

        response.setId(reserva.getId());
        response.setCodigoReserva(reserva.getCodigoReserva());
        response.setEmailHuesped(reserva.getHuesped().getEmail());
        response.setNumeroHabitacion(reserva.getHabitacion().getNumeroHabitacion());
        response.setFechaEntrada(reserva.getFechaEntrada());
        response.setFechaSalida(reserva.getFechaSalida());
        response.setEstado(reserva.getEstado());
        response.setCreadoEn(reserva.getCreadoEn());

        return response;
    }
}