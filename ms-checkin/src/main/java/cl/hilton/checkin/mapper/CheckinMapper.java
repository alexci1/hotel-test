package cl.hilton.checkin.mapper;

import cl.hilton.checkin.dto.CheckinRequest;
import cl.hilton.checkin.dto.CheckinResponse;
import cl.hilton.checkin.model.Checkin;
import cl.hilton.checkin.model.ProjHuesped;
import cl.hilton.checkin.model.ProjReserva;
import org.springframework.stereotype.Component;

@Component
public class CheckinMapper {

    public Checkin toEntity(CheckinRequest request, ProjReserva reserva, ProjHuesped huesped) {
        return Checkin.builder()
                .reserva(reserva)
                .huesped(huesped)
                .numeroHabitacion(request.getNumeroHabitacion())
                .fechaHora(request.getFechaHora())
                .realizadoPor(request.getRealizadoPor())
                .build();
    }

    public CheckinResponse toResponse(Checkin checkin) {
        return CheckinResponse.builder()
                .id(checkin.getId())
                .codigoReserva(checkin.getReserva() != null ? checkin.getReserva().getCodigoReserva() : null)
                .emailHuesped(checkin.getHuesped() != null ? checkin.getHuesped().getEmail() : null)
                .nombreHuesped(checkin.getHuesped() != null ? checkin.getHuesped().getNombreCompleto() : null)
                .numeroHabitacion(checkin.getNumeroHabitacion())
                .fechaHora(checkin.getFechaHora())
                .realizadoPor(checkin.getRealizadoPor())
                .build();
    }

    public void updateEntity(Checkin checkin, CheckinRequest request, ProjReserva reserva, ProjHuesped huesped) {
        checkin.setReserva(reserva);
        checkin.setHuesped(huesped);
        checkin.setNumeroHabitacion(request.getNumeroHabitacion());
        checkin.setFechaHora(request.getFechaHora());
        checkin.setRealizadoPor(request.getRealizadoPor());
    }
}
