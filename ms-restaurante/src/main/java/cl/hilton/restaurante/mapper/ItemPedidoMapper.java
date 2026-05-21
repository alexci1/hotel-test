package cl.hilton.restaurante.mapper;


import cl.hilton.restaurante.dto.ItemPedidoRequest;
import cl.hilton.restaurante.dto.ItemPedidoResponse;
import cl.hilton.restaurante.model.ItemPedido;
import cl.hilton.restaurante.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class ItemPedidoMapper {

    public ItemPedido toEntity(ItemPedidoRequest request, Pedido pedido) {
        return ItemPedido.builder()
                .pedido(pedido)
                .nombreProducto(request.getNombreProducto())
                .cantidad(request.getCantidad())
                .precioUnitUsd(request.getPrecioUnitUsd())
                .observacion(request.getObservacion())
                .build();
    }

    public ItemPedidoResponse toResponse(ItemPedido item) {
        return ItemPedidoResponse.builder()
                .id(item.getId())
                .numeroPedido(item.getPedido().getNumeroPedido())
                .nombreProducto(item.getNombreProducto())
                .cantidad(item.getCantidad())
                .precioUnitUsd(item.getPrecioUnitUsd())
                .observacion(item.getObservacion())
                .build();
    }

    public void updateEntity(ItemPedido item, ItemPedidoRequest request, Pedido pedido) {
        item.setPedido(pedido);
        item.setNombreProducto(request.getNombreProducto());
        item.setCantidad(request.getCantidad());
        item.setPrecioUnitUsd(request.getPrecioUnitUsd());
        item.setObservacion(request.getObservacion());
    }
}
