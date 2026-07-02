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

    @Operation(summary = "Listar movimientos", description = "Retorna todos los movimientos registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimientos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimientoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron movimientos", content = @Content)
    })
    @GetMapping
    public CollectionModel<MovimientoResponse> findAll() {
        List<MovimientoResponse> list = movimientoService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener movimiento por ID", description = "Retorna un movimiento según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimiento encontrado",
            content = @Content(schema = @Schema(implementation = MovimientoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Movimiento no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public MovimientoResponse findById(@PathVariable Long id) {
        return addLinks(movimientoService.findById(id));
    }

    @Operation(summary = "Listar movimientos por producto", description = "Retorna movimientos según código de producto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimientos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimientoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron movimientos", content = @Content)
    })
    @GetMapping("/producto/{codigoProducto}")
    public CollectionModel<MovimientoResponse> findByCodigoProducto(@PathVariable String codigoProducto) {
        List<MovimientoResponse> list = movimientoService.findByCodigoProducto(codigoProducto);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByCodigoProducto(codigoProducto)).withSelfRel());
    }

    @Operation(summary = "Listar movimientos por tipo", description = "Retorna movimientos según tipo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimientos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimientoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron movimientos", content = @Content)
    })
    @GetMapping("/tipo/{tipo}")
    public CollectionModel<MovimientoResponse> findByTipo(@PathVariable String tipo) {
        List<MovimientoResponse> list = movimientoService.findByTipo(tipo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByTipo(tipo)).withSelfRel());
    }

    @Operation(summary = "Listar movimientos por responsable", description = "Retorna movimientos según responsable")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimientos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimientoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron movimientos", content = @Content)
    })
    @GetMapping("/registrado-por/{registradoPor}")
    public CollectionModel<MovimientoResponse> findByRegistradoPor(@PathVariable String registradoPor) {
        List<MovimientoResponse> list = movimientoService.findByRegistradoPor(registradoPor);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByRegistradoPor(registradoPor)).withSelfRel());
    }

    @Operation(summary = "Listar movimientos por fecha", description = "Retorna movimientos según fecha")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimientos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimientoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron movimientos", content = @Content)
    })
    @GetMapping("/fecha/{registradoEn}")
    public CollectionModel<MovimientoResponse> findByRegistradoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate registradoEn) {
        List<MovimientoResponse> list = movimientoService.findByRegistradoEn(registradoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByRegistradoEn(registradoEn)).withSelfRel());
    }

    @Operation(summary = "Listar movimientos por rango de fechas", description = "Retorna movimientos entre dos fechas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimientos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimientoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron movimientos", content = @Content)
    })
    @GetMapping("/rango")
    public CollectionModel<MovimientoResponse> findByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        List<MovimientoResponse> list = movimientoService.findByRangoFechas(desde, hasta);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByRangoFechas(desde, hasta)).withSelfRel());
    }

    @Operation(summary = "Listar movimientos con cantidad mayor", description = "Retorna movimientos con cantidad mayor al valor indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimientos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimientoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron movimientos", content = @Content)
    })
    @GetMapping("/cantidad-mayor/{cantidad}")
    public CollectionModel<MovimientoResponse> findByCantidadGreaterThan(@PathVariable Integer cantidad) {
        List<MovimientoResponse> list = movimientoService.findByCantidadGreaterThan(cantidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MovimientoController.class).findByCantidadGreaterThan(cantidad)).withSelfRel());
    }

    @Operation(summary = "Listar movimientos con cantidad inferior", description = "Retorna movimientos con cantidad inferior al valor indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movimientos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimientoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron movimientos", content = @Content)
    })
    @GetMapping("/cantidad-" + "menor/{cantidad}")
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
