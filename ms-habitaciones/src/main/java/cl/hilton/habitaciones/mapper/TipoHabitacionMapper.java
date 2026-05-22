package cl.hilton.habitaciones.mapper;

import cl.hilton.habitaciones.dto.TipoHabitacionRequest;
import cl.hilton.habitaciones.dto.TipoHabitacionResponse;
import cl.hilton.habitaciones.model.TipoHabitacion;

public class TipoHabitacionMapper {

    public static TipoHabitacion toEntity(TipoHabitacionRequest request) {
        return TipoHabitacion.builder()
                .codigo(request.getCodigo())
                .descripcion(request.getDescripcion())
                .capacidadMax(request.getCapacidadMax())
                .activo(request.getActivo())
                .build();
    }

    public static TipoHabitacionResponse toResponse(TipoHabitacion tipoHabitacion) {
        TipoHabitacionResponse response = new TipoHabitacionResponse();

        response.setId(tipoHabitacion.getId());
        response.setCodigo(tipoHabitacion.getCodigo());
        response.setDescripcion(tipoHabitacion.getDescripcion());
        response.setCapacidadMax(tipoHabitacion.getCapacidadMax());
        response.setActivo(tipoHabitacion.getActivo());

        return response;
    }
}