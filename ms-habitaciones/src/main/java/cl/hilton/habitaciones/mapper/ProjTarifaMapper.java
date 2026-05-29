package cl.hilton.habitaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.habitaciones.dto.ProjTarifaRequest;
import cl.hilton.habitaciones.dto.ProjTarifaResponse;
import cl.hilton.habitaciones.model.ProjTarifa;

@Mapper(componentModel = "spring")
public interface ProjTarifaMapper {

    @Mapping(target = "actualizadoEn", ignore = true)
    ProjTarifa toEntity(ProjTarifaRequest request);

    ProjTarifaResponse toResponse(ProjTarifa tarifa);

    List<ProjTarifaResponse> toResponseList(List<ProjTarifa> tarifas);

    @Mapping(target = "tipoHabitacion", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    void updateEntity(ProjTarifaRequest request, @MappingTarget ProjTarifa tarifa);
}
