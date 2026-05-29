package cl.hilton.restaurante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.restaurante.dto.ProjHuespedRequest;
import cl.hilton.restaurante.dto.ProjHuespedResponse;
import cl.hilton.restaurante.model.ProjHuesped;

@Mapper(componentModel = "spring")
public interface ProjHuespedMapper {

    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    ProjHuesped toEntity(ProjHuespedRequest request);

    ProjHuespedResponse toResponse(ProjHuesped huesped);

    List<ProjHuespedResponse> toResponseList(List<ProjHuesped> huespedes);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    void updateEntity(ProjHuespedRequest request, @MappingTarget ProjHuesped huesped);
}
