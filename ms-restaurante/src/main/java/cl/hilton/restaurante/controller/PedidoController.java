package cl.hilton.restaurante.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.restaurante.dto.PedidoRequest;
import cl.hilton.restaurante.dto.PedidoResponse;
import cl.hilton.restaurante.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public List<PedidoResponse> findAll() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public PedidoResponse findById(@PathVariable Long id) {
        return pedidoService.findById(id);
    }

    @GetMapping("/numero/{numeroPedido}")
    public PedidoResponse findByNumeroPedido(@PathVariable String numeroPedido) {
        return pedidoService.findByNumeroPedido(numeroPedido);
    }

    @GetMapping("/estado/{estado}")
    public List<PedidoResponse> findByEstado(@PathVariable String estado) {
        return pedidoService.findByEstado(estado);
    }

    @GetMapping("/mesa/{numeroMesa}")
    public List<PedidoResponse> findByNumeroMesa(@PathVariable String numeroMesa) {
        return pedidoService.findByNumeroMesa(numeroMesa);
    }

    @GetMapping("/huesped/{emailHuesped}")
    public List<PedidoResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        return pedidoService.findByEmailHuesped(emailHuesped);
    }

    @GetMapping("/rango")
    public List<PedidoResponse> findByRangoCreadoEn(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return pedidoService.findByRangoCreadoEn(desde, hasta);
    }

    @GetMapping("/mesa/{numeroMesa}/estado/{estado}")
    public List<PedidoResponse> findByNumeroMesaAndEstado(
            @PathVariable String numeroMesa,
            @PathVariable String estado) {
        return pedidoService.findByNumeroMesaAndEstado(numeroMesa, estado);
    }

    @GetMapping("/huesped/{emailHuesped}/estado/{estado}")
    public List<PedidoResponse> findByEmailHuespedAndEstado(
            @PathVariable String emailHuesped,
            @PathVariable String estado) {
        return pedidoService.findByEmailHuespedAndEstado(emailHuesped, estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse create(@Valid @RequestBody PedidoRequest request) {
        return pedidoService.create(request);
    }

    @PutMapping("/{id}")
    public PedidoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody PedidoRequest request) {
        return pedidoService.update(id, request);
    }

    @PatchMapping("/{id}/estado")
    public PedidoResponse cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return pedidoService.cambiarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        pedidoService.deleteById(id);
    }
}
