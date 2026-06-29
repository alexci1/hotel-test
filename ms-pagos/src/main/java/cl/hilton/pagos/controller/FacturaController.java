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

import cl.hilton.pagos.dto.FacturaRequest;
import cl.hilton.pagos.dto.FacturaResponse;
import cl.hilton.pagos.service.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    private FacturaResponse addLinks(FacturaResponse f) {
        f.add(linkTo(methodOn(FacturaController.class).findById(f.getId())).withSelfRel());
        f.add(linkTo(methodOn(FacturaController.class).update(f.getId(), null)).withRel("update").withTitle("PUT - Actualizar factura"));
        f.add(linkTo(methodOn(FacturaController.class).deleteById(f.getId())).withRel("delete").withTitle("DELETE - Eliminar factura"));
        f.add(linkTo(methodOn(FacturaController.class).findByEmailHuesped(f.getEmailHuesped())).withRel("facturas-huesped").withTitle("GET - Facturas del huesped"));
        f.add(linkTo(methodOn(FacturaController.class).findAll()).withRel("all").withTitle("GET - Todas las facturas"));
        return f;
    }

    @GetMapping
    public CollectionModel<FacturaResponse> findAll() {
        List<FacturaResponse> list = facturaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(FacturaController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public FacturaResponse findById(@PathVariable Long id) {
        return addLinks(facturaService.findById(id));
    }

    @GetMapping("/numero/{numeroFactura}")
    public FacturaResponse findByNumeroFactura(@PathVariable String numeroFactura) {
        return addLinks(facturaService.findByNumeroFactura(numeroFactura));
    }

    @GetMapping("/reserva/{codigoReserva}")
    public FacturaResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return addLinks(facturaService.findByCodigoReserva(codigoReserva));
    }

    @GetMapping("/huesped/{emailHuesped}")
    public CollectionModel<FacturaResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        List<FacturaResponse> list = facturaService.findByEmailHuesped(emailHuesped);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(FacturaController.class).findByEmailHuesped(emailHuesped)).withSelfRel());
    }

    @GetMapping("/estado/{estado}")
    public CollectionModel<FacturaResponse> findByEstado(@PathVariable String estado) {
        List<FacturaResponse> list = facturaService.findByEstado(estado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(FacturaController.class).findByEstado(estado)).withSelfRel());
    }

    @GetMapping("/fecha/{emitidaEn}")
    public CollectionModel<FacturaResponse> findByEmitidaEn(@PathVariable LocalDate emitidaEn) {
        List<FacturaResponse> list = facturaService.findByEmitidaEn(emitidaEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(FacturaController.class).findByEmitidaEn(emitidaEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaResponse create(@Valid @RequestBody FacturaRequest request) {
        return addLinks(facturaService.create(request));
    }

    @PutMapping("/{id}")
    public FacturaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody FacturaRequest request) {
        return addLinks(facturaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        facturaService.deleteById(id);
    }
}
