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

    @Operation(summary = "Listar minibares", description = "Retorna todos los registros de minibar del inventario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Minibares encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MiniBarResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron minibares", content = @Content)
    })
    @GetMapping
    public CollectionModel<MiniBarResponse> findAll() {
        List<MiniBarResponse> list = miniBarService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener minibar por ID", description = "Retorna un registro de minibar según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Minibar encontrado",
            content = @Content(schema = @Schema(implementation = MiniBarResponse.class))),
        @ApiResponse(responseCode = "404", description = "Minibar no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public MiniBarResponse findById(@PathVariable Long id) {
        return addLinks(miniBarService.findById(id));
    }

    @Operation(summary = "Listar minibares por habitación", description = "Retorna los registros de minibar asociados a un número de habitación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Minibares encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MiniBarResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron minibares para la habitación indicada", content = @Content)
    })
    @GetMapping("/habitacion/{numeroHabitacion}")
    public CollectionModel<MiniBarResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        List<MiniBarResponse> list = miniBarService.findByNumeroHabitacion(numeroHabitacion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findByNumeroHabitacion(numeroHabitacion)).withSelfRel());
    }

    @Operation(summary = "Listar minibares por producto", description = "Retorna los registros de minibar asociados a un código de producto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Minibares encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MiniBarResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron minibares para el producto indicado", content = @Content)
    })
    @GetMapping("/producto/{codigoProducto}")
    public CollectionModel<MiniBarResponse> findByCodigoProducto(@PathVariable String codigoProducto) {
        List<MiniBarResponse> list = miniBarService.findByCodigoProducto(codigoProducto);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findByCodigoProducto(codigoProducto)).withSelfRel());
    }

    @Operation(summary = "Obtener minibar por habitación y producto", description = "Retorna un registro de minibar según habitación y código de producto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Minibar encontrado",
            content = @Content(schema = @Schema(implementation = MiniBarResponse.class))),
        @ApiResponse(responseCode = "404", description = "Minibar no encontrado para la habitación y producto indicados", content = @Content)
    })
    @GetMapping("/habitacion/{numeroHabitacion}/producto/{codigoProducto}")
    public MiniBarResponse findByHabitacionAndProducto(
            @PathVariable String numeroHabitacion,
            @PathVariable String codigoProducto) {
        return addLinks(miniBarService.findByHabitacionAndProducto(numeroHabitacion, codigoProducto));
    }

    @Operation(summary = "Listar minibares por cantidad", description = "Retorna los registros de minibar filtrados por cantidad")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Minibares encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MiniBarResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron minibares con la cantidad indicada", content = @Content)
    })
    @GetMapping("/cantidad/{cantidad}")
    public CollectionModel<MiniBarResponse> findByCantidad(@PathVariable Integer cantidad) {
        List<MiniBarResponse> list = miniBarService.findByCantidad(cantidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findByCantidad(cantidad)).withSelfRel());
    }

    @Operation(summary = "Listar minibares con cantidad mayor", description = "Retorna los registros de minibar cuya cantidad es mayor al valor indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Minibares encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MiniBarResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron minibares sobre la cantidad indicada", content = @Content)
    })
    @GetMapping("/cantidad-mayor/{cantidad}")
    public CollectionModel<MiniBarResponse> findByCantidadGreaterThan(@PathVariable Integer cantidad) {
        List<MiniBarResponse> list = miniBarService.findByCantidadGreaterThan(cantidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MiniBarController.class).findByCantidadGreaterThan(cantidad)).withSelfRel());
    }

    @Operation(summary = "Listar minibares con precio mayor", description = "Retorna los registros de minibar cuyo precio unitario USD es mayor al valor indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Minibares encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MiniBarResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron minibares sobre el precio indicado", content = @Content)
    })
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
