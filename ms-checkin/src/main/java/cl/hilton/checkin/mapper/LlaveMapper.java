package cl.hilton.checkin.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cl.hilton.checkin.dto.LlaveRequest;
import cl.hilton.checkin.dto.LlaveResponse;
import cl.hilton.checkin.model.Llave;
import cl.hilton.checkin.model.ProjReserva;

@Component
public class LlaveMapper {

    public Llave toEntity(LlaveRequest request, ProjReserva reserva) {
        if (request == null && reserva == null) {
            return null;
        }

        Llave llave = new Llave();
        llave.setReserva(reserva);

        if (request != null) {
            llave.setNumeroHabitacion(request.getNumeroHabitacion());
            llave.setCodigoLlave(request.getCodigoLlave());
            llave.setActiva(request.getActiva());
        }

        return llave;
    }

    public LlaveResponse toResponse(Llave llave) {
        if (llave == null) {
            return null;
        }

        LlaveResponse response = new LlaveResponse();
        response.setId(llave.getId());
        response.setNumeroHabitacion(llave.getNumeroHabitacion());
        response.setCodigoLlave(llave.getCodigoLlave());
        response.setActiva(llave.getActiva());
        response.setEmitidaEn(llave.getEmitidaEn());

        if (llave.getReserva() != null) {
            response.setCodigoReserva(llave.getReserva().getCodigoReserva());
        }

        return response;
    }

    public List<LlaveResponse> toResponseList(List<Llave> llaves) {
        if (llaves == null) {
            return null;
        }

        return llaves.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(LlaveRequest request, ProjReserva reserva, Llave llave) {
        if (llave == null) {
            return;
        }

        llave.setReserva(reserva);

        if (request != null) {
            llave.setNumeroHabitacion(request.getNumeroHabitacion());
            llave.setCodigoLlave(request.getCodigoLlave());
            llave.setActiva(request.getActiva());
        }
    }
}
