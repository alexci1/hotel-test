package cl.hilton.habitaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.habitaciones.dto.EstadoHabitacionRequest;
import cl.hilton.habitaciones.dto.EstadoHabitacionResponse;
import cl.hilton.habitaciones.model.EstadoHabitacion;

@Mapper(componentModel = "spring")
public interface EstadoHabitacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    EstadoHabitacion toEntity(EstadoHabitacionRequest request);

    @Mapping(target = "numeroHabitacion", source = "habitacion.numeroHabitacion")
    EstadoHabitacionResponse toResponse(EstadoHabitacion estadoHabitacion);

    List<EstadoHabitacionResponse> toResponseList(List<EstadoHabitacion> estadosHabitacion);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    void updateEntity(EstadoHabitacionRequest request, @MappingTarget EstadoHabitacion estadoHabitacion);
}
