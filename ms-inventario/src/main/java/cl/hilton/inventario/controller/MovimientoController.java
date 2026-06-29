package cl.hilton.inventario.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.inventario.dto.MovimientoRequest;
import cl.hilton.inventario.dto.MovimientoResponse;
import cl.hilton.inventario.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    private MovimientoResponse addLinks(MovimientoResponse m) {
        m.add(linkTo(methodOn(MovimientoController.class).findById(m.getId())).withSelfRel());
        m.add(linkTo(methodOn(MovimientoController.class).update(m.getId(), null)).withRel("update"));
        m.add(linkTo(MovimientoController.class).slash(m.getId()).withRel("delete"));
        m.add(linkTo(methodOn(MovimientoController.class).findAll()).withRel("all"));
        return m;
    }

    @GetMapping
    public CollectionModel<MovimientoResponse> findAll() {
        List<MovimientoResponse> list = movimientoService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public MovimientoResponse findById(@PathVariable Long id) {
        return addLinks(movimientoService.findById(id));
    }

    @GetMapping("/producto/{codigoProducto}")
    public CollectionModel<MovimientoResponse> findByCodigoProducto(@PathVariable String codigoProducto) {
        List<MovimientoResponse> list = movimientoService.findByCodigoProducto(codigoProducto);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByCodigoProducto(codigoProducto)).withSelfRel());
    }

    @GetMapping("/tipo/{tipo}")
    public CollectionModel<MovimientoResponse> findByTipo(@PathVariable String tipo) {
        List<MovimientoResponse> list = movimientoService.findByTipo(tipo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByTipo(tipo)).withSelfRel());
    }

    @GetMapping("/registrado-por/{registradoPor}")
    public CollectionModel<MovimientoResponse> findByRegistradoPor(@PathVariable String registradoPor) {
        List<MovimientoResponse> list = movimientoService.findByRegistradoPor(registradoPor);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByRegistradoPor(registradoPor)).withSelfRel());
    }

    @GetMapping("/fecha/{registradoEn}")
    public CollectionModel<MovimientoResponse> findByRegistradoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate registradoEn) {
        List<MovimientoResponse> list = movimientoService.findByRegistradoEn(registradoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByRegistradoEn(registradoEn)).withSelfRel());
    }

    @GetMapping("/rango")
    public CollectionModel<MovimientoResponse> findByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        List<MovimientoResponse> list = movimientoService.findByRangoFechas(desde, hasta);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByRangoFechas(desde, hasta)).withSelfRel());
    }

    @GetMapping("/cantidad-mayor/{cantidad}")
    public CollectionModel<MovimientoResponse> findByCantidadGreaterThan(@PathVariable Integer cantidad) {
        List<MovimientoResponse> list = movimientoService.findByCantidadGreaterThan(cantidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByCantidadGreaterThan(cantidad)).withSelfRel());
    }

    @GetMapping("/cantidad-menor/{cantidad}")
    public CollectionModel<MovimientoResponse> findByCantidadLessThan(@PathVariable Integer cantidad) {
        List<MovimientoResponse> list = movimientoService.findByCantidadLessThan(cantidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByCantidadLessThan(cantidad)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse create(@Valid @RequestBody MovimientoRequest request) {
        return addLinks(movimientoService.create(request));
    }

    @PutMapping("/{id}")
    public MovimientoResponse update(@PathVariable Long id, @Valid @RequestBody MovimientoRequest request) {
        return addLinks(movimientoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        movimientoService.deleteById(id);
    }
}
