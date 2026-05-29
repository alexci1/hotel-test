package cl.hilton.restaurante.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.restaurante.dto.ItemPedidoRequest;
import cl.hilton.restaurante.dto.ItemPedidoResponse;
import cl.hilton.restaurante.service.ItemPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/restaurante/items-pedidos")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @GetMapping
    public List<ItemPedidoResponse> findAll() {
        return itemPedidoService.findAll();
    }

    @GetMapping("/{id}")
    public ItemPedidoResponse findById(@PathVariable Long id) {
        return itemPedidoService.findById(id);
    }

    @GetMapping("/pedido/{numeroPedido}")
    public List<ItemPedidoResponse> findByNumeroPedido(@PathVariable String numeroPedido) {
        return itemPedidoService.findByNumeroPedido(numeroPedido);
    }

    @GetMapping("/producto/{nombreProducto}")
    public List<ItemPedidoResponse> findByNombreProducto(@PathVariable String nombreProducto) {
        return itemPedidoService.findByNombreProducto(nombreProducto);
    }

    @GetMapping("/cantidad-mayor-que/{cantidad}")
    public List<ItemPedidoResponse> findByCantidadMayorQue(@PathVariable Integer cantidad) {
        return itemPedidoService.findByCantidadMayorQue(cantidad);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemPedidoResponse create(@Valid @RequestBody ItemPedidoRequest request) {
        return itemPedidoService.create(request);
    }

    @PutMapping("/{id}")
    public ItemPedidoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ItemPedidoRequest request) {
        return itemPedidoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        itemPedidoService.deleteById(id);
    }
}
