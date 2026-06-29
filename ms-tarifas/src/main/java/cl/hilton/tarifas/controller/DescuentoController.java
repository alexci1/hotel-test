package cl.hilton.tarifas.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.tarifas.dto.DescuentoRequest;
import cl.hilton.tarifas.dto.DescuentoResponse;
import cl.hilton.tarifas.service.DescuentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/descuentos")
@RequiredArgsConstructor
public class DescuentoController {

    private final DescuentoService descuentoService;

    private DescuentoResponse addLinks(DescuentoResponse d) {
        d.add(linkTo(methodOn(DescuentoController.class).findById(d.getId())).withSelfRel());
        d.add(linkTo(methodOn(DescuentoController.class).update(d.getId(), null)).withRel("update"));
        d.add(linkTo(methodOn(DescuentoController.class).findById(d.getId())).withRel("delete"));
        d.add(linkTo(methodOn(DescuentoController.class).findAll()).withRel("all"));
        return d;
    }

    @GetMapping
    public CollectionModel<DescuentoResponse> findAll() {
        List<DescuentoResponse> list = descuentoService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DescuentoController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public DescuentoResponse findById(@PathVariable Long id) {
        return addLinks(descuentoService.findById(id));
    }

    @GetMapping("/codigo/{codigoDescuento}")
    public DescuentoResponse findByCodigoDescuento(@PathVariable String codigoDescuento) {
        return addLinks(descuentoService.findByCodigoDescuento(codigoDescuento));
    }

    @GetMapping("/aplica-a/{aplicaA}")
    public CollectionModel<DescuentoResponse> findByAplicaA(@PathVariable String aplicaA) {
        List<DescuentoResponse> list = descuentoService.findByAplicaA(aplicaA);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DescuentoController.class).findByAplicaA(aplicaA)).withSelfRel());
    }

    @GetMapping("/activo/{activo}")
    public CollectionModel<DescuentoResponse> findByActivo(@PathVariable Boolean activo) {
        List<DescuentoResponse> list = descuentoService.findByActivo(activo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DescuentoController.class).findByActivo(activo)).withSelfRel());
    }

    @GetMapping("/valido-desde/{validoDesde}")
    public CollectionModel<DescuentoResponse> findByValidoDesde(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validoDesde) {
        List<DescuentoResponse> list = descuentoService.findByValidoDesde(validoDesde);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DescuentoController.class).findByValidoDesde(validoDesde)).withSelfRel());
    }

    @GetMapping("/valido-hasta/{validoHasta}")
    public CollectionModel<DescuentoResponse> findByValidoHasta(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validoHasta) {
        List<DescuentoResponse> list = descuentoService.findByValidoHasta(validoHasta);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DescuentoController.class).findByValidoHasta(validoHasta)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DescuentoResponse create(@Valid @RequestBody DescuentoRequest request) {
        return addLinks(descuentoService.create(request));
    }

    @PutMapping("/{id}")
    public DescuentoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody DescuentoRequest request) {
        return addLinks(descuentoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        descuentoService.deleteById(id);
    }
}
