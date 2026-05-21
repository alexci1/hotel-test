package cl.hilton.inventario.mapper;

import cl.hilton.inventario.dto.ProjHabitacionRequest;
import cl.hilton.inventario.dto.ProjHabitacionResponse;
import cl.hilton.inventario.model.ProjHabitacion;
import org.springframework.stereotype.Component;

@Component
public class ProjHabitacionMapper {

    public ProjHabitacion toEntity(ProjHabitacionRequest request) {
        return ProjHabitacion.builder()
                .numeroHabitacion(request.getNumeroHabitacion())
                .tipo(request.getTipo())
                .actualizadoEn(request.getActualizadoEn())
                .build();
    }

    public ProjHabitacionResponse toResponse(ProjHabitacion habitacion) {
        return ProjHabitacionResponse.builder()
                .numeroHabitacion(habitacion.getNumeroHabitacion())
                .tipo(habitacion.getTipo())
                .actualizadoEn(habitacion.getActualizadoEn())
                .build();
    }

    public void updateEntity(ProjHabitacion habitacion, ProjHabitacionRequest request) {
        habitacion.setTipo(request.getTipo());
        habitacion.setActualizadoEn(request.getActualizadoEn());
    }
}

