package cl.hilton.tarifas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.tarifas.dto.TarifaRequest;
import cl.hilton.tarifas.dto.TarifaResponse;
import cl.hilton.tarifas.model.Tarifa;

@Mapper(componentModel = "spring")
public interface TarifaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "temporada", ignore = true)
    @Mapping(target = "tipoHabitacion", ignore = true)
    Tarifa toEntity(TarifaRequest request);

    @Mapping(target = "codigoTemporada", source = "temporada.codigo")
    @Mapping(target = "codigoTipoHabitacion", source = "tipoHabitacion.codigo")
    TarifaResponse toResponse(Tarifa tarifa);

    List<TarifaResponse> toResponseList(List<Tarifa> tarifas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "temporada", ignore = true)
    @Mapping(target = "tipoHabitacion", ignore = true)
    void updateEntity(TarifaRequest request, @MappingTarget Tarifa tarifa);
}
