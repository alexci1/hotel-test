package cl.hilton.reservas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.reservas.dto.DisponibilidadRequest;
import cl.hilton.reservas.dto.DisponibilidadResponse;
import cl.hilton.reservas.model.Disponibilidad;

@Mapper(componentModel = "spring")
public interface DisponibilidadMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    Disponibilidad toEntity(DisponibilidadRequest request);

    @Mapping(target = "numeroHabitacion", source = "habitacion.numeroHabitacion")
    DisponibilidadResponse toResponse(Disponibilidad disponibilidad);

    List<DisponibilidadResponse> toResponseList(List<Disponibilidad> disponibilidades);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    void updateEntity(DisponibilidadRequest request, @MappingTarget Disponibilidad disponibilidad);
}
