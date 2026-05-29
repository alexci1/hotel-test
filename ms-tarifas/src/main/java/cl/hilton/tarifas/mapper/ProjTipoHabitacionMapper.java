package cl.hilton.tarifas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.tarifas.dto.ProjTipoHabitacionRequest;
import cl.hilton.tarifas.dto.ProjTipoHabitacionResponse;
import cl.hilton.tarifas.model.ProjTipoHabitacion;

@Mapper(componentModel = "spring")
public interface ProjTipoHabitacionMapper {

    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "tarifas", ignore = true)
    ProjTipoHabitacion toEntity(ProjTipoHabitacionRequest request);

    ProjTipoHabitacionResponse toResponse(ProjTipoHabitacion tipoHabitacion);

    List<ProjTipoHabitacionResponse> toResponseList(List<ProjTipoHabitacion> tiposHabitacion);

    @Mapping(target = "codigo", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "tarifas", ignore = true)
    void updateEntity(ProjTipoHabitacionRequest request, @MappingTarget ProjTipoHabitacion tipoHabitacion);
}
