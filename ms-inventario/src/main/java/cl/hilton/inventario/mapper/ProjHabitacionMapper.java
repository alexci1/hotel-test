package cl.hilton.inventario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.inventario.dto.ProjHabitacionRequest;
import cl.hilton.inventario.dto.ProjHabitacionResponse;
import cl.hilton.inventario.model.ProjHabitacion;

@Mapper(componentModel = "spring")
public interface ProjHabitacionMapper {

    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "minibares", ignore = true)
    ProjHabitacion toEntity(ProjHabitacionRequest request);

    ProjHabitacionResponse toResponse(ProjHabitacion habitacion);

    List<ProjHabitacionResponse> toResponseList(List<ProjHabitacion> habitaciones);

    @Mapping(target = "numeroHabitacion", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "minibares", ignore = true)
    void updateEntity(ProjHabitacionRequest request, @MappingTarget ProjHabitacion habitacion);
}
