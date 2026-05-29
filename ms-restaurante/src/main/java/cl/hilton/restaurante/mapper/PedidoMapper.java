package cl.hilton.restaurante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.restaurante.dto.PedidoRequest;
import cl.hilton.restaurante.dto.PedidoResponse;
import cl.hilton.restaurante.model.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mesa", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "items", ignore = true)
    Pedido toEntity(PedidoRequest request);

    @Mapping(target = "numeroMesa", source = "mesa.numeroMesa")
    @Mapping(target = "emailHuesped", source = "huesped.email")
    @Mapping(target = "nombreHuesped", source = "huesped.nombreCompleto")
    PedidoResponse toResponse(Pedido pedido);

    List<PedidoResponse> toResponseList(List<Pedido> pedidos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mesa", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateEntity(PedidoRequest request, @MappingTarget Pedido pedido);
}
