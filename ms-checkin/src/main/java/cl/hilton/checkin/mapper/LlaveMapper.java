package cl.hilton.checkin.mapper;

import cl.hilton.checkin.dto.LlaveRequest;
import cl.hilton.checkin.dto.LlaveResponse;
import cl.hilton.checkin.model.Llave;
import cl.hilton.checkin.model.ProjReserva;
import org.springframework.stereotype.Component;

@Component
public class LlaveMapper {

    public Llave toEntity(LlaveRequest request, ProjReserva reserva) {
        return Llave.builder()
                .numeroHabitacion(request.getNumeroHabitacion())
                .codigoLlave(request.getCodigoLlave())
                .activa(request.getActiva())
                .reserva(reserva)
                .emitidaEn(request.getEmitidaEn())
                .build();
    }

    public LlaveResponse toResponse(Llave llave) {
        return LlaveResponse.builder()
                .id(llave.getId())
                .numeroHabitacion(llave.getNumeroHabitacion())
                .codigoLlave(llave.getCodigoLlave())
                .activa(llave.getActiva())
                .codigoReserva(llave.getReserva() != null ? llave.getReserva().getCodigoReserva() : null)
                .emitidaEn(llave.getEmitidaEn())
                .build();
    }

    public void updateEntity(Llave llave, LlaveRequest request, ProjReserva reserva) {
        llave.setNumeroHabitacion(request.getNumeroHabitacion());
        llave.setCodigoLlave(request.getCodigoLlave());
        llave.setActiva(request.getActiva());
        llave.setReserva(reserva);
        llave.setEmitidaEn(request.getEmitidaEn());
    }
}