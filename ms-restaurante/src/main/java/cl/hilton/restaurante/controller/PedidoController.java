package cl.hilton.restaurante.controller;

import java.util.List;

import cl.hilton.restaurante.dto.PedidoRequest;
import cl.hilton.restaurante.dto.PedidoResponse;
import cl.hilton.restaurante.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<PedidoResponse> listar() {
        return pedidoService.listar();
    }

    @GetMapping("/{id}")
    public PedidoResponse buscarPorId(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id);
    }

    @GetMapping("/numero/{numeroPedido}")
    public PedidoResponse buscarPorNumeroPedido(@PathVariable String numeroPedido) {
        return pedidoService.buscarPorNumeroPedido(numeroPedido);
    }

    @GetMapping("/estado/{estado}")
    public List<PedidoResponse> buscarPorEstado(@PathVariable String estado) {
        return pedidoService.buscarPorEstado(estado);
    }

    @GetMapping("/mesa/{numeroMesa}")
    public List<PedidoResponse> buscarPorMesa(@PathVariable String numeroMesa) {
        return pedidoService.buscarPorMesa(numeroMesa);
    }

    @GetMapping("/huesped/{email}")
    public List<PedidoResponse> buscarPorHuesped(@PathVariable String email) {
        return pedidoService.buscarPorHuesped(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse crear(@Valid @RequestBody PedidoRequest request) {
        return pedidoService.crear(request);
    }

    @PutMapping("/{id}")
    public PedidoResponse actualizar(@PathVariable Integer id, @Valid @RequestBody PedidoRequest request) {
        return pedidoService.actualizar(id, request);
    }

    @PatchMapping("/{id}/estado")
    public PedidoResponse cambiarEstado(@PathVariable Integer id, @RequestParam String estado) {
        return pedidoService.cambiarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        pedidoService.eliminar(id);
    }
}