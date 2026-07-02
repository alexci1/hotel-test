package cl.hilton.restaurante.controller;

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

import cl.hilton.restaurante.dto.ItemPedidoRequest;
import cl.hilton.restaurante.dto.ItemPedidoResponse;
import cl.hilton.restaurante.service.ItemPedidoService;
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
@RequestMapping("/api/v1/items-pedidos")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    private ItemPedidoResponse addLinks(ItemPedidoResponse i) {
        i.add(linkTo(methodOn(ItemPedidoController.class).findById(i.getId())).withSelfRel());
        i.add(linkTo(methodOn(ItemPedidoController.class).update(i.getId(), null)).withRel("update"));
        i.add(linkTo(ItemPedidoController.class).slash(i.getId()).withRel("delete"));
        i.add(linkTo(methodOn(ItemPedidoController.class).findAll()).withRel("all"));
        return i;
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemPedidoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping
    public CollectionModel<ItemPedidoResponse> findAll() {
        List<ItemPedidoResponse> list = itemPedidoService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ItemPedidoController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener registro", description = "Obtiene registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = ItemPedidoResponse.class))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ItemPedidoResponse findById(@PathVariable Long id) {
        return addLinks(itemPedidoService.findById(id));
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemPedidoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/pedido/{numeroPedido}")
    public CollectionModel<ItemPedidoResponse> findByNumeroPedido(@PathVariable String numeroPedido) {
        List<ItemPedidoResponse> list = itemPedidoService.findByNumeroPedido(numeroPedido);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ItemPedidoController.class).findByNumeroPedido(numeroPedido)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemPedidoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/producto/{nombreProducto}")
    public CollectionModel<ItemPedidoResponse> findByNombreProducto(@PathVariable String nombreProducto) {
        List<ItemPedidoResponse> list = itemPedidoService.findByNombreProducto(nombreProducto);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ItemPedidoController.class).findByNombreProducto(nombreProducto)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemPedidoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/cantidad-mayor-que/{cantidad}")
    public CollectionModel<ItemPedidoResponse> findByCantidadMayorQue(@PathVariable Integer cantidad) {
        List<ItemPedidoResponse> list = itemPedidoService.findByCantidadMayorQue(cantidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ItemPedidoController.class).findByCantidadMayorQue(cantidad)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemPedidoResponse create(@Valid @RequestBody ItemPedidoRequest request) {
        return addLinks(itemPedidoService.create(request));
    }

    @PutMapping("/{id}")
    public ItemPedidoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ItemPedidoRequest request) {
        return addLinks(itemPedidoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        itemPedidoService.deleteById(id);
    }
}
