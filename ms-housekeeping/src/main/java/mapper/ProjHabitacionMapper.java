package cl.hilton.housekeeping.mapper;

import cl.hilton.housekeeping.dto.ProjHabitacionRequest;
import cl.hilton.housekeeping.dto.ProjHabitacionResponse;
import cl.hilton.housekeeping.model.ProjHabitacion;
import org.springframework.stereotype.Component;

@Component
public class ProjHabitacionMapper {

    public ProjHabitacion toEntity(ProjHabitacionRequest request) {
        return ProjHabitacion.builder()
                .numeroHabitacion(request.getNumeroHabitacion())
                .tipo(request.getTipo())
                .piso(request.getPiso())
                .actualizadoEn(request.getActualizadoEn())
                .build();
    }

    public ProjHabitacionResponse toResponse(ProjHabitacion habitacion) {
        return ProjHabitacionResponse.builder()
                .numeroHabitacion(habitacion.getNumeroHabitacion())
                .tipo(habitacion.getTipo())
                .piso(habitacion.getPiso())
                .actualizadoEn(habitacion.getActualizadoEn())
                .build();
    }

    public void updateEntity(ProjHabitacion habitacion, ProjHabitacionRequest request) {
        habitacion.setTipo(request.getTipo());
        habitacion.setPiso(request.getPiso());
        habitacion.setActualizadoEn(request.getActualizadoEn());
    }
}