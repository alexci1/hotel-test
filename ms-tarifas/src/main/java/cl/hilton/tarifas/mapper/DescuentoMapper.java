package cl.hilton.tarifas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.tarifas.dto.DescuentoRequest;
import cl.hilton.tarifas.dto.DescuentoResponse;
import cl.hilton.tarifas.model.Descuento;

@Mapper(componentModel = "spring")
public interface DescuentoMapper {

    @Mapping(target = "id", ignore = true)
    Descuento toEntity(DescuentoRequest request);

    DescuentoResponse toResponse(Descuento descuento);

    List<DescuentoResponse> toResponseList(List<Descuento> descuentos);

    @Mapping(target = "id", ignore = true)
    void updateEntity(DescuentoRequest request, @MappingTarget Descuento descuento);
}
