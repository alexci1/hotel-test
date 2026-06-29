package cl.hilton.pagos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
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

import cl.hilton.pagos.dto.PagoRequest;
import cl.hilton.pagos.dto.PagoResponse;
import cl.hilton.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    private PagoResponse addLinks(PagoResponse p) {
        p.add(linkTo(methodOn(PagoController.class).findById(p.getId())).withSelfRel());
        p.add(linkTo(methodOn(PagoController.class).update(p.getId(), null)).withRel("update").withTitle("PUT - Actualizar pago"));
        p.add(linkTo(PagoController.class).slash(p.getId()).withRel("delete"));
        p.add(linkTo(methodOn(PagoController.class).findAll()).withRel("all").withTitle("GET - Todos los pagos"));
        return p;
    }

    @GetMapping
    public CollectionModel<PagoResponse> findAll() {
        List<PagoResponse> list = pagoService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PagoController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public PagoResponse findById(@PathVariable Long id) {
        return addLinks(pagoService.findById(id));
    }

    @GetMapping("/factura/{numeroFactura}")
    public CollectionModel<PagoResponse> findByNumeroFactura(@PathVariable String numeroFactura) {
        List<PagoResponse> list = pagoService.findByNumeroFactura(numeroFactura);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PagoController.class).findByNumeroFactura(numeroFactura)).withSelfRel());
    }

    @GetMapping("/metodo/{metodo}")
    public CollectionModel<PagoResponse> findByMetodo(@PathVariable String metodo) {
        List<PagoResponse> list = pagoService.findByMetodo(metodo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PagoController.class).findByMetodo(metodo)).withSelfRel());
    }

    @GetMapping("/fecha/{pagadoEn}")
    public CollectionModel<PagoResponse> findByPagadoEn(@PathVariable LocalDate pagadoEn) {
        List<PagoResponse> list = pagoService.findByPagadoEn(pagadoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PagoController.class).findByPagadoEn(pagadoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagoResponse create(@Valid @RequestBody PagoRequest request) {
        return addLinks(pagoService.create(request));
    }

    @PutMapping("/{id}")
    public PagoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody PagoRequest request) {
        return addLinks(pagoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        pagoService.deleteById(id);
    }
}
