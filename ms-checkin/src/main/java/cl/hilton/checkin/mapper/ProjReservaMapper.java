package cl.hilton.checkin.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cl.hilton.checkin.dto.ProjReservaRequest;
import cl.hilton.checkin.dto.ProjReservaResponse;
import cl.hilton.checkin.model.ProjReserva;

@Component
public class ProjReservaMapper {

    public ProjReserva toEntity(ProjReservaRequest request) {
        if (request == null) {
            return null;
        }

        ProjReserva reserva = new ProjReserva();
        reserva.setCodigoReserva(request.getCodigoReserva());
        reserva.setEmailHuesped(request.getEmailHuesped());
        reserva.setNumeroHabitacion(request.getNumeroHabitacion());
        reserva.setFechaEntrada(request.getFechaEntrada());
        reserva.setFechaSalida(request.getFechaSalida());
        reserva.setEstado(request.getEstado());
        return reserva;
    }

    public ProjReservaResponse toResponse(ProjReserva reserva) {
        if (reserva == null) {
            return null;
        }

        ProjReservaResponse response = new ProjReservaResponse();
        response.setCodigoReserva(reserva.getCodigoReserva());
        response.setEmailHuesped(reserva.getEmailHuesped());
        response.setNumeroHabitacion(reserva.getNumeroHabitacion());
        response.setFechaEntrada(reserva.getFechaEntrada());
        response.setFechaSalida(reserva.getFechaSalida());
        response.setEstado(reserva.getEstado());
        return response;
    }

    public List<ProjReservaResponse> toResponseList(List<ProjReserva> reservas) {
        if (reservas == null) {
            return null;
        }

        return reservas.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(ProjReservaRequest request, ProjReserva reserva) {
        if (request == null || reserva == null) {
            return;
        }

        reserva.setEmailHuesped(request.getEmailHuesped());
        reserva.setNumeroHabitacion(request.getNumeroHabitacion());
        reserva.setFechaEntrada(request.getFechaEntrada());
        reserva.setFechaSalida(request.getFechaSalida());
        reserva.setEstado(request.getEstado());
    }
}
