package cl.hilton.restaurante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.restaurante.dto.ItemPedidoRequest;
import cl.hilton.restaurante.dto.ItemPedidoResponse;
import cl.hilton.restaurante.model.ItemPedido;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    ItemPedido toEntity(ItemPedidoRequest request);

    @Mapping(target = "numeroPedido", source = "pedido.numeroPedido")
    ItemPedidoResponse toResponse(ItemPedido item);

    List<ItemPedidoResponse> toResponseList(List<ItemPedido> items);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    void updateEntity(ItemPedidoRequest request, @MappingTarget ItemPedido item);
}
