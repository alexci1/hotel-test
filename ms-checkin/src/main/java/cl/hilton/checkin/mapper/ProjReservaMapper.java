package cl.hilton.checkin.mapper;

import cl.hilton.checkin.dto.ProjReservaRequest;
import cl.hilton.checkin.dto.ProjReservaResponse;
import cl.hilton.checkin.model.ProjReserva;
import org.springframework.stereotype.Component;

@Component
public class ProjReservaMapper {

    public ProjReserva toEntity(ProjReservaRequest request) {
        return ProjReserva.builder()
                .codigoReserva(request.getCodigoReserva())
                .emailHuesped(request.getEmailHuesped())
                .numeroHabitacion(request.getNumeroHabitacion())
                .fechaEntrada(request.getFechaEntrada())
                .fechaSalida(request.getFechaSalida())
                .estado(request.getEstado())
                .actualizadoEn(request.getActualizadoEn())
                .build();
    }

    public ProjReservaResponse toResponse(ProjReserva reserva) {
        return ProjReservaResponse.builder()
                .codigoReserva(reserva.getCodigoReserva())
                .emailHuesped(reserva.getEmailHuesped())
                .numeroHabitacion(reserva.getNumeroHabitacion())
                .fechaEntrada(reserva.getFechaEntrada())
                .fechaSalida(reserva.getFechaSalida())
                .estado(reserva.getEstado())
                .actualizadoEn(reserva.getActualizadoEn())
                .build();
    }

    public void updateEntity(ProjReserva reserva, ProjReservaRequest request) {
        reserva.setEmailHuesped(request.getEmailHuesped());
        reserva.setNumeroHabitacion(request.getNumeroHabitacion());
        reserva.setFechaEntrada(request.getFechaEntrada());
        reserva.setFechaSalida(request.getFechaSalida());
        reserva.setEstado(request.getEstado());
        reserva.setActualizadoEn(request.getActualizadoEn());
    }
}
