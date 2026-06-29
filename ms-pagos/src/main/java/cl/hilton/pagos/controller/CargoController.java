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

import cl.hilton.pagos.dto.CargoRequest;
import cl.hilton.pagos.dto.CargoResponse;
import cl.hilton.pagos.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService cargoService;

    private CargoResponse addLinks(CargoResponse c) {
        c.add(linkTo(methodOn(CargoController.class).findById(c.getId())).withSelfRel());
        c.add(linkTo(methodOn(CargoController.class).update(c.getId(), null)).withRel("update").withTitle("PUT - Actualizar cargo"));
        c.add(linkTo(CargoController.class).slash(c.getId()).withRel("delete"));
        c.add(linkTo(methodOn(CargoController.class).findAll()).withRel("all").withTitle("GET - Todos los cargos"));
        return c;
    }

    @GetMapping
    public CollectionModel<CargoResponse> findAll() {
        List<CargoResponse> list = cargoService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CargoController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public CargoResponse findById(@PathVariable Long id) {
        return addLinks(cargoService.findById(id));
    }

    @GetMapping("/factura/{numeroFactura}")
    public CollectionModel<CargoResponse> findByNumeroFactura(@PathVariable String numeroFactura) {
        List<CargoResponse> list = cargoService.findByNumeroFactura(numeroFactura);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CargoController.class).findByNumeroFactura(numeroFactura)).withSelfRel());
    }

    @GetMapping("/origen/{origen}")
    public CollectionModel<CargoResponse> findByOrigen(@PathVariable String origen) {
        List<CargoResponse> list = cargoService.findByOrigen(origen);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CargoController.class).findByOrigen(origen)).withSelfRel());
    }

    @GetMapping("/fecha/{registradoEn}")
    public CollectionModel<CargoResponse> findByRegistradoEn(@PathVariable LocalDate registradoEn) {
        List<CargoResponse> list = cargoService.findByRegistradoEn(registradoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CargoController.class).findByRegistradoEn(registradoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CargoResponse create(@Valid @RequestBody CargoRequest request) {
        return addLinks(cargoService.create(request));
    }

    @PutMapping("/{id}")
    public CargoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CargoRequest request) {
        return addLinks(cargoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        cargoService.deleteById(id);
    }
}
