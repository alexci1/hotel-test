package cl.hilton.reservas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.reservas.dto.ProjHabitacionRequest;
import cl.hilton.reservas.dto.ProjHabitacionResponse;
import cl.hilton.reservas.model.ProjHabitacion;

@Mapper(componentModel = "spring")
public interface ProjHabitacionMapper {

    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "disponibilidades", ignore = true)
    ProjHabitacion toEntity(ProjHabitacionRequest request);

    ProjHabitacionResponse toResponse(ProjHabitacion habitacion);

    List<ProjHabitacionResponse> toResponseList(List<ProjHabitacion> habitaciones);

    @Mapping(target = "numeroHabitacion", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "disponibilidades", ignore = true)
    void updateEntity(ProjHabitacionRequest request, @MappingTarget ProjHabitacion habitacion);
}
