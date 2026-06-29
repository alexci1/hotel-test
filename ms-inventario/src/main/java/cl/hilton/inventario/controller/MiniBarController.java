package cl.hilton.inventario.controller;

import java.util.List;

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

import cl.hilton.inventario.dto.MiniBarRequest;
import cl.hilton.inventario.dto.MiniBarResponse;
import cl.hilton.inventario.service.MinibarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/minibares")
@RequiredArgsConstructor
public class MiniBarController {

    private final MinibarService miniBarService;

    private MiniBarResponse addLinks(MiniBarResponse m) {
        m.add(linkTo(methodOn(MiniBarController.class).findById(m.getId())).withSelfRel());
        m.add(linkTo(methodOn(MiniBarController.class).update(m.getId(), null)).withRel("update"));
        m.add(linkTo(methodOn(MiniBarController.class).findById(m.getId())).withRel("delete"));
        m.add(linkTo(methodOn(MiniBarController.class).actualizarCantidad(m.getId(), null)).withRel("actualizar-cantidad"));
        m.add(linkTo(methodOn(MiniBarController.class).findAll()).withRel("all"));
        return m;
    }

    @GetMapping
    public CollectionModel<MiniBarResponse> findAll() {
        List<MiniBarResponse> list = miniBarService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public MiniBarResponse findById(@PathVariable Long id) {
        return addLinks(miniBarService.findById(id));
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public CollectionModel<MiniBarResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        List<MiniBarResponse> list = miniBarService.findByNumeroHabitacion(numeroHabitacion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findByNumeroHabitacion(numeroHabitacion)).withSelfRel());
    }

    @GetMapping("/producto/{codigoProducto}")
    public CollectionModel<MiniBarResponse> findByCodigoProducto(@PathVariable String codigoProducto) {
        List<MiniBarResponse> list = miniBarService.findByCodigoProducto(codigoProducto);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findByCodigoProducto(codigoProducto)).withSelfRel());
    }

    @GetMapping("/habitacion/{numeroHabitacion}/producto/{codigoProducto}")
    public MiniBarResponse findByHabitacionAndProducto(
            @PathVariable String numeroHabitacion,
            @PathVariable String codigoProducto) {
        return addLinks(miniBarService.findByHabitacionAndProducto(numeroHabitacion, codigoProducto));
    }

    @GetMapping("/cantidad/{cantidad}")
    public CollectionModel<MiniBarResponse> findByCantidad(@PathVariable Integer cantidad) {
        List<MiniBarResponse> list = miniBarService.findByCantidad(cantidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findByCantidad(cantidad)).withSelfRel());
    }

    @GetMapping("/cantidad-mayor/{cantidad}")
    public CollectionModel<MiniBarResponse> findByCantidadGreaterThan(@PathVariable Integer cantidad) {
        List<MiniBarResponse> list = miniBarService.findByCantidadGreaterThan(cantidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findByCantidadGreaterThan(cantidad)).withSelfRel());
    }

    @GetMapping("/precio-mayor/{precioUnitUsd}")
    public CollectionModel<MiniBarResponse> findByPrecioUnitUsdGreaterThan(@PathVariable Integer precioUnitUsd) {
        List<MiniBarResponse> list = miniBarService.findByPrecioUnitUsdGreaterThan(precioUnitUsd);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findByPrecioUnitUsdGreaterThan(precioUnitUsd)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MiniBarResponse create(@Valid @RequestBody MiniBarRequest request) {
        return addLinks(miniBarService.create(request));
    }

    @PutMapping("/{id}")
    public MiniBarResponse update(@PathVariable Long id, @Valid @RequestBody MiniBarRequest request) {
        return addLinks(miniBarService.update(id, request));
    }

    @PatchMapping("/{id}/cantidad")
    public MiniBarResponse actualizarCantidad(@PathVariable Long id, @RequestParam Integer cantidad) {
        return addLinks(miniBarService.actualizarCantidad(id, cantidad));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        miniBarService.deleteById(id);
    }
}
