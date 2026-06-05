package cl.hilton.restaurante.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.restaurante.dto.ItemPedidoRequest;
import cl.hilton.restaurante.dto.ItemPedidoResponse;
import cl.hilton.restaurante.mapper.ItemPedidoMapper;
import cl.hilton.restaurante.model.ItemPedido;
import cl.hilton.restaurante.model.Pedido;
import cl.hilton.restaurante.repository.ItemPedidoRepository;
import cl.hilton.restaurante.repository.PedidoRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoMapper itemPedidoMapper;

    public List<ItemPedidoResponse> findAll() {
        return itemPedidoMapper.toResponseList(itemPedidoRepository.findAll());
    }

    public ItemPedidoResponse findById(Long id) {
        ItemPedido item = getItemById(id);
        return itemPedidoMapper.toResponse(item);
    }

    public List<ItemPedidoResponse> findByNumeroPedido(String numeroPedido) {
        return itemPedidoMapper.toResponseList(itemPedidoRepository.findByPedidoNumeroPedido(numeroPedido));
    }

    public List<ItemPedidoResponse> findByNombreProducto(String nombreProducto) {
        return itemPedidoMapper.toResponseList(itemPedidoRepository.findByNombreProductoContainingIgnoreCase(nombreProducto));
    }

    public List<ItemPedidoResponse> findByCantidadMayorQue(Integer cantidad) {
        return itemPedidoMapper.toResponseList(itemPedidoRepository.findByCantidadGreaterThan(cantidad));
    }

    public ItemPedidoResponse create(ItemPedidoRequest request) {
        Pedido pedido = pedidoRepository.findByNumeroPedido(request.getNumeroPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con numero: " + request.getNumeroPedido()));

        ItemPedido item = itemPedidoMapper.toEntity(request);
        item.setPedido(pedido);
        item.setCantidad(request.getCantidad() != null ? request.getCantidad() : 1);

        ItemPedido itemGuardado = itemPedidoRepository.save(item);

        return itemPedidoMapper.toResponse(itemGuardado);
    }

    public ItemPedidoResponse update(Long id, ItemPedidoRequest request) {
        ItemPedido item = getItemById(id);

        Pedido pedido = pedidoRepository.findByNumeroPedido(request.getNumeroPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con numero: " + request.getNumeroPedido()));

        itemPedidoMapper.updateEntity(request, item);
        item.setPedido(pedido);
        item.setCantidad(request.getCantidad() != null ? request.getCantidad() : item.getCantidad());

        ItemPedido itemActualizado = itemPedidoRepository.save(item);

        return itemPedidoMapper.toResponse(itemActualizado);
    }

    public void deleteById(Long id) {
        ItemPedido item = getItemById(id);
        itemPedidoRepository.delete(item);
    }

    private ItemPedido getItemById(Long id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item de pedido no encontrado con id: " + id));
    }
}
