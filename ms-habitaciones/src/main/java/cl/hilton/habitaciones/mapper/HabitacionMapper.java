package cl.hilton.habitaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.habitaciones.dto.HabitacionRequest;
import cl.hilton.habitaciones.dto.HabitacionResponse;
import cl.hilton.habitaciones.model.Habitacion;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoHabitacion", ignore = true)
    @Mapping(target = "estadosHabitacion", ignore = true)
    Habitacion toEntity(HabitacionRequest request);

    @Mapping(target = "codigoTipo", source = "tipoHabitacion.codigo")
    HabitacionResponse toResponse(Habitacion habitacion);

    List<HabitacionResponse> toResponseList(List<Habitacion> habitaciones);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoHabitacion", ignore = true)
    @Mapping(target = "estadosHabitacion", ignore = true)
    void updateEntity(HabitacionRequest request, @MappingTarget Habitacion habitacion);
}
