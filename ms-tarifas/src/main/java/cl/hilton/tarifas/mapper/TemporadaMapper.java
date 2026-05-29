package cl.hilton.tarifas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.tarifas.dto.TemporadaRequest;
import cl.hilton.tarifas.dto.TemporadaResponse;
import cl.hilton.tarifas.model.Temporada;

@Mapper(componentModel = "spring")
public interface TemporadaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tarifas", ignore = true)
    Temporada toEntity(TemporadaRequest request);

    TemporadaResponse toResponse(Temporada temporada);

    List<TemporadaResponse> toResponseList(List<Temporada> temporadas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tarifas", ignore = true)
    void updateEntity(TemporadaRequest request, @MappingTarget Temporada temporada);
}
