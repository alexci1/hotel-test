package cl.hilton.restaurante.service;


import cl.hilton.restaurante.dto.ItemPedidoRequest;
import cl.hilton.restaurante.dto.ItemPedidoResponse;
import cl.hilton.restaurante.model.ItemPedido;
import cl.hilton.restaurante.model.Pedido;
import cl.hilton.restaurante.repository.ItemPedidoRepository;
import cl.hilton.restaurante.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;

    public List<ItemPedidoResponse> listar() {
        return itemPedidoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ItemPedidoResponse buscarPorId(Integer id) {
        return toResponse(obtenerItem(id));
    }

    public List<ItemPedidoResponse> buscarPorPedido(String numeroPedido) {
        return itemPedidoRepository.findByPedidoNumeroPedido(numeroPedido).stream().map(this::toResponse).toList();
    }

    public List<ItemPedidoResponse> buscarPorNombreProducto(String nombreProducto) {
        return itemPedidoRepository.findByNombreProductoContainingIgnoreCase(nombreProducto)
                .stream().map(this::toResponse).toList();
    }

    public ItemPedidoResponse crear(ItemPedidoRequest request) {
        Pedido pedido = pedidoRepository.findByNumeroPedido(request.getNumeroPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        ItemPedido item = ItemPedido.builder()
                .pedido(pedido)
                .nombreProducto(request.getNombreProducto())
                .cantidad(request.getCantidad())
                .precioUnitUsd(request.getPrecioUnitUsd())
                .observacion(request.getObservacion())
                .build();

        return toResponse(itemPedidoRepository.save(item));
    }

    public ItemPedidoResponse actualizar(Integer id, ItemPedidoRequest request) {
        ItemPedido item = obtenerItem(id);
        Pedido pedido = pedidoRepository.findByNumeroPedido(request.getNumeroPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        item.setPedido(pedido);
        item.setNombreProducto(request.getNombreProducto());
        item.setCantidad(request.getCantidad());
        item.setPrecioUnitUsd(request.getPrecioUnitUsd());
        item.setObservacion(request.getObservacion());

        return toResponse(itemPedidoRepository.save(item));
    }

    public void eliminar(Integer id) {
        ItemPedido item = obtenerItem(id);
        itemPedidoRepository.delete(item);
    }

    private ItemPedido obtenerItem(Integer id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de pedido no encontrado"));
    }

    private ItemPedidoResponse toResponse(ItemPedido item) {
        return ItemPedidoResponse.builder()
                .id(item.getId())
                .numeroPedido(item.getPedido().getNumeroPedido())
                .nombreProducto(item.getNombreProducto())
                .cantidad(item.getCantidad())
                .precioUnitUsd(item.getPrecioUnitUsd())
                .observacion(item.getObservacion())
                .build();
    }
}

