package cl.hilton.restaurante.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    private PedidoResponse addLinks(PedidoResponse p) {
        p.add(linkTo(methodOn(PedidoController.class).findById(p.getId())).withSelfRel());
        p.add(linkTo(methodOn(PedidoController.class).update(p.getId(), null)).withRel("update").withTitle("PUT - Actualizar pedido"));
        p.add(linkTo(methodOn(PedidoController.class).deleteById(p.getId())).withRel("delete").withTitle("DELETE - Eliminar pedido"));
        p.add(linkTo(methodOn(PedidoController.class).cambiarEstado(p.getId(), null)).withRel("cambiar-estado").withTitle("PATCH - Cambiar estado"));
        p.add(linkTo(methodOn(PedidoController.class).findByEstado(p.getEstado())).withRel("pedidos-estado").withTitle("GET - Pedidos por estado"));
        p.add(linkTo(methodOn(PedidoController.class).findAll()).withRel("all").withTitle("GET - Todos los pedidos"));
        return p;
    }

    @GetMapping
    public CollectionModel<PedidoResponse> findAll() {
        List<PedidoResponse> list = pedidoService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PedidoController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public PedidoResponse findById(@PathVariable Long id) {
        return addLinks(pedidoService.findById(id));
    }

    @GetMapping("/numero/{numeroPedido}")
    public PedidoResponse findByNumeroPedido(@PathVariable String numeroPedido) {
        return addLinks(pedidoService.findByNumeroPedido(numeroPedido));
    }

    @GetMapping("/estado/{estado}")
    public CollectionModel<PedidoResponse> findByEstado(@PathVariable String estado) {
        List<PedidoResponse> list = pedidoService.findByEstado(estado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PedidoController.class).findByEstado(estado)).withSelfRel());
    }

    @GetMapping("/mesa/{numeroMesa}")
    public CollectionModel<PedidoResponse> findByNumeroMesa(@PathVariable String numeroMesa) {
        List<PedidoResponse> list = pedidoService.findByNumeroMesa(numeroMesa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PedidoController.class).findByNumeroMesa(numeroMesa)).withSelfRel());
    }

    @GetMapping("/huesped/{emailHuesped}")
    public CollectionModel<PedidoResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        List<PedidoResponse> list = pedidoService.findByEmailHuesped(emailHuesped);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PedidoController.class).findByEmailHuesped(emailHuesped)).withSelfRel());
    }

    @GetMapping("/rango")
    public CollectionModel<PedidoResponse> findByRangoCreadoEn(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        List<PedidoResponse> list = pedidoService.findByRangoCreadoEn(desde, hasta);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PedidoController.class).findByRangoCreadoEn(desde, hasta)).withSelfRel());
    }

    @GetMapping("/mesa/{numeroMesa}/estado/{estado}")
    public CollectionModel<PedidoResponse> findByNumeroMesaAndEstado(
            @PathVariable String numeroMesa,
            @PathVariable String estado) {
        List<PedidoResponse> list = pedidoService.findByNumeroMesaAndEstado(numeroMesa, estado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PedidoController.class).findByNumeroMesaAndEstado(numeroMesa, estado)).withSelfRel());
    }

    @GetMapping("/huesped/{emailHuesped}/estado/{estado}")
    public CollectionModel<PedidoResponse> findByEmailHuespedAndEstado(
            @PathVariable String emailHuesped,
            @PathVariable String estado) {
        List<PedidoResponse> list = pedidoService.findByEmailHuespedAndEstado(emailHuesped, estado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PedidoController.class).findByEmailHuespedAndEstado(emailHuesped, estado)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse create(@Valid @RequestBody PedidoRequest request) {
        return addLinks(pedidoService.create(request));
    }

    @PutMapping("/{id}")
    public PedidoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody PedidoRequest request) {
        return addLinks(pedidoService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public PedidoResponse cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return addLinks(pedidoService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        pedidoService.deleteById(id);
    }
}
