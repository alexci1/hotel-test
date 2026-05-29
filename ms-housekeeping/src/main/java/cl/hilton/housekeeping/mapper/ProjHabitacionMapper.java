package cl.hilton.housekeeping.mapper;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import cl.hilton.housekeeping.dto.ProjHabitacionRequest;
import cl.hilton.housekeeping.dto.ProjHabitacionResponse;
import cl.hilton.housekeeping.model.ProjHabitacion;

@Component
public class ProjHabitacionMapper {

    public ProjHabitacion toEntity(ProjHabitacionRequest request) {
        return ProjHabitacion.builder()
                .numeroHabitacion(request.getNumeroHabitacion())
                .tipo(request.getTipo())
                .piso(request.getPiso())
                .actualizadoEn(LocalDate.now())
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

    public List<ProjHabitacionResponse> toResponseList(List<ProjHabitacion> habitaciones) {
        return habitaciones.stream().map(this::toResponse).toList();
    }

    public void updateEntity(ProjHabitacion habitacion, ProjHabitacionRequest request) {
        habitacion.setTipo(request.getTipo());
        habitacion.setPiso(request.getPiso());
        habitacion.setActualizadoEn(LocalDate.now());
    }
}