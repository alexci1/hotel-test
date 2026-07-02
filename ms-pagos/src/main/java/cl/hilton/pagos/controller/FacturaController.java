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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        f.add(linkTo(methodOn(FacturaController.class).update(f.getId(), null)).withRel("update"));
        f.add(linkTo(FacturaController.class).slash(f.getId()).withRel("delete"));
        f.add(linkTo(methodOn(FacturaController.class).findByEmailHuesped(f.getEmailHuesped())).withRel("related"));
        f.add(linkTo(methodOn(FacturaController.class).findAll()).withRel("all"));
        return f;
    }

    @Operation(summary = "Listar registros", description = "Retorna todos los registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FacturaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping
    public CollectionModel<FacturaResponse> findAll() {
        List<FacturaResponse> list = facturaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(FacturaController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener registro", description = "Retorna un registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = FacturaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public FacturaResponse findById(@PathVariable Long id) {
        return addLinks(facturaService.findById(id));
    }

    @Operation(summary = "Obtener registro", description = "Retorna un registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = FacturaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    @GetMapping("/numero/{numeroFactura}")
    public FacturaResponse findByNumeroFactura(@PathVariable String numeroFactura) {
        return addLinks(facturaService.findByNumeroFactura(numeroFactura));
    }

    @Operation(summary = "Obtener registro", description = "Retorna un registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = FacturaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    @GetMapping("/reserva/{codigoReserva}")
    public FacturaResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return addLinks(facturaService.findByCodigoReserva(codigoReserva));
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FacturaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/huesped/{emailHuesped}")
    public CollectionModel<FacturaResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        List<FacturaResponse> list = facturaService.findByEmailHuesped(emailHuesped);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(FacturaController.class).findByEmailHuesped(emailHuesped)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FacturaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/estado/{estado}")
    public CollectionModel<FacturaResponse> findByEstado(@PathVariable String estado) {
        List<FacturaResponse> list = facturaService.findByEstado(estado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(FacturaController.class).findByEstado(estado)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FacturaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
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
