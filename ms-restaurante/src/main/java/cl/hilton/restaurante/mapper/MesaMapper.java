package cl.hilton.restaurante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.restaurante.dto.MesaRequest;
import cl.hilton.restaurante.dto.MesaResponse;
import cl.hilton.restaurante.model.Mesa;

@Mapper(componentModel = "spring")
public interface MesaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    Mesa toEntity(MesaRequest request);

    MesaResponse toResponse(Mesa mesa);

    List<MesaResponse> toResponseList(List<Mesa> mesas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    void updateEntity(MesaRequest request, @MappingTarget Mesa mesa);
}
