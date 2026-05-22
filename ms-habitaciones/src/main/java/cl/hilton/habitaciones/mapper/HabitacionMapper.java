package cl.hilton.habitaciones.mapper;

import cl.hilton.habitaciones.dto.HabitacionRequest;
import cl.hilton.habitaciones.dto.HabitacionResponse;
import cl.hilton.habitaciones.model.Habitacion;
import cl.hilton.habitaciones.model.TipoHabitacion;

public class HabitacionMapper {

    public static Habitacion toEntity(HabitacionRequest request, TipoHabitacion tipoHabitacion) {
        return Habitacion.builder()
                .numeroHabitacion(request.getNumeroHabitacion())
                .piso(request.getPiso())
                .tipoHabitacion(tipoHabitacion)
                .activa(request.getActiva())
                .build();
    }

    public static HabitacionResponse toResponse(Habitacion habitacion) {
        HabitacionResponse response = new HabitacionResponse();

        response.setId(habitacion.getId());
        response.setNumeroHabitacion(habitacion.getNumeroHabitacion());
        response.setPiso(habitacion.getPiso());
        response.setCodigoTipo(habitacion.getTipoHabitacion().getCodigo());
        response.setActiva(habitacion.getActiva());

        return response;
    }
}