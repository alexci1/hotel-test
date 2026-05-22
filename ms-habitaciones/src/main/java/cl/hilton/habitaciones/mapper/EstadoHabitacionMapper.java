package cl.hilton.habitaciones.mapper;

import cl.hilton.habitaciones.dto.EstadoHabitacionRequest;
import cl.hilton.habitaciones.dto.EstadoHabitacionResponse;
import cl.hilton.habitaciones.model.EstadoHabitacion;
import cl.hilton.habitaciones.model.Habitacion;

public class EstadoHabitacionMapper {

    public static EstadoHabitacion toEntity(EstadoHabitacionRequest request, Habitacion habitacion) {
        return EstadoHabitacion.builder()
                .habitacion(habitacion)
                .estado(request.getEstado())
                .observacion(request.getObservacion())
                .build();
    }

    public static EstadoHabitacionResponse toResponse(EstadoHabitacion estadoHabitacion) {
        EstadoHabitacionResponse response = new EstadoHabitacionResponse();

        response.setId(estadoHabitacion.getId());
        response.setNumeroHabitacion(estadoHabitacion.getHabitacion().getNumeroHabitacion());
        response.setEstado(estadoHabitacion.getEstado());
        response.setObservacion(estadoHabitacion.getObservacion());
        response.setActualizadoEn(estadoHabitacion.getActualizadoEn());

        return response;
    }
}