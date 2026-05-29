package cl.hilton.habitaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.habitaciones.dto.TipoHabitacionRequest;
import cl.hilton.habitaciones.dto.TipoHabitacionResponse;
import cl.hilton.habitaciones.model.TipoHabitacion;

@Mapper(componentModel = "spring")
public interface TipoHabitacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitaciones", ignore = true)
    TipoHabitacion toEntity(TipoHabitacionRequest request);

    TipoHabitacionResponse toResponse(TipoHabitacion tipoHabitacion);

    List<TipoHabitacionResponse> toResponseList(List<TipoHabitacion> tiposHabitacion);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitaciones", ignore = true)
    void updateEntity(TipoHabitacionRequest request, @MappingTarget TipoHabitacion tipoHabitacion);
}
