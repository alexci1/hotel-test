package cl.hilton.restaurante.controller;

import cl.hilton.restaurante.dto.ItemPedidoRequest;
import cl.hilton.restaurante.dto.ItemPedidoResponse;
import cl.hilton.restaurante.service.ItemPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items-pedido")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @GetMapping
    public List<ItemPedidoResponse> listar() {
        return itemPedidoService.listar();
    }

    @GetMapping("/{id}")
    public ItemPedidoResponse buscarPorId(@PathVariable Long id) {
        return itemPedidoService.buscarPorId(id);
    }

    @GetMapping("/pedido/{numeroPedido}")
    public List<ItemPedidoResponse> buscarPorPedido(@PathVariable String numeroPedido) {
        return itemPedidoService.buscarPorPedido(numeroPedido);
    }

    @GetMapping("/producto")
    public List<ItemPedidoResponse> buscarPorNombreProducto(@RequestParam String nombreProducto) {
        return itemPedidoService.buscarPorNombreProducto(nombreProducto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemPedidoResponse crear(@Valid @RequestBody ItemPedidoRequest request) {
        return itemPedidoService.crear(request);
    }

    @PutMapping("/{id}")
    public ItemPedidoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ItemPedidoRequest request) {
        return itemPedidoService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        itemPedidoService.eliminar(id);
    }
}