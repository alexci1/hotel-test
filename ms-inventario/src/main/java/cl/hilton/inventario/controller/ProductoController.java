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

import cl.hilton.inventario.dto.ProductoRequest;
import cl.hilton.inventario.dto.ProductoResponse;
import cl.hilton.inventario.service.ProductoService;
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
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    private ProductoResponse addLinks(ProductoResponse p) {
        p.add(linkTo(methodOn(ProductoController.class).findById(p.getId())).withSelfRel());
        p.add(linkTo(methodOn(ProductoController.class).update(p.getId(), null)).withRel("update"));
        p.add(linkTo(methodOn(ProductoController.class).findById(p.getId())).withRel("delete"));
        p.add(linkTo(methodOn(ProductoController.class).ajustarStock(p.getId(), null)).withRel("ajustar-stock"));
        p.add(linkTo(methodOn(ProductoController.class).findAll()).withRel("all"));
        return p;
    }

    @Operation(summary = "Listar productos", description = "Retorna todos los productos registrados en el inventario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos", content = @Content)
    })
    @GetMapping
    public CollectionModel<ProductoResponse> findAll() {
        List<ProductoResponse> list = productoService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ProductoController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
            content = @Content(schema = @Schema(implementation = ProductoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ProductoResponse findById(@PathVariable Long id) {
        return addLinks(productoService.findById(id));
    }

    @Operation(summary = "Obtener producto por código", description = "Retorna un producto según su código único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
            content = @Content(schema = @Schema(implementation = ProductoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/codigo/{codigoProducto}")
    public ProductoResponse findByCodigoProducto(@PathVariable String codigoProducto) {
        return addLinks(productoService.findByCodigoProducto(codigoProducto));
    }

    @Operation(summary = "Listar productos por categoría", description = "Retorna los productos asociados a una categoría")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos para la categoría indicada", content = @Content)
    })
    @GetMapping("/categoria/{categoria}")
    public CollectionModel<ProductoResponse> findByCategoria(@PathVariable String categoria) {
        List<ProductoResponse> list = productoService.findByCategoria(categoria);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ProductoController.class).findByCategoria(categoria)).withSelfRel());
    }

    @Operation(summary = "Listar productos por unidad", description = "Retorna los productos asociados a una unidad de medida")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos para la unidad indicada", content = @Content)
    })
    @GetMapping("/unidad/{unidad}")
    public CollectionModel<ProductoResponse> findByUnidad(@PathVariable String unidad) {
        List<ProductoResponse> list = productoService.findByUnidad(unidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ProductoController.class).findByUnidad(unidad)).withSelfRel());
    }

    @Operation(summary = "Buscar productos por nombre", description = "Retorna los productos que coinciden con el nombre indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos con el nombre indicado", content = @Content)
    })
    @GetMapping("/buscar")
    public CollectionModel<ProductoResponse> findByNombre(@RequestParam String nombre) {
        List<ProductoResponse> list = productoService.findByNombre(nombre);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ProductoController.class).findByNombre(nombre)).withSelfRel());
    }

    @Operation(summary = "Listar productos con stock menor o igual", description = "Retorna los productos cuyo stock actual es menor o igual al valor indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos con el stock indicado", content = @Content)
    })
    @GetMapping("/stock-menor-igual/{stockActual}")
    public CollectionModel<ProductoResponse> findByStockActualLessThanEqual(@PathVariable Integer stockActual) {
        List<ProductoResponse> list = productoService.findByStockActualLessThanEqual(stockActual);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ProductoController.class).findByStockActualLessThanEqual(stockActual)).withSelfRel());
    }

    @Operation(summary = "Listar productos con stock menor", description = "Retorna los productos cuyo stock actual es menor al valor indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos bajo el stock indicado", content = @Content)
    })
    @GetMapping("/stock-menor/{stockActual}")
    public CollectionModel<ProductoResponse> findByStockActualLessThan(@PathVariable Integer stockActual) {
        List<ProductoResponse> list = productoService.findByStockActualLessThan(stockActual);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ProductoController.class).findByStockActualLessThan(stockActual)).withSelfRel());
    }

    @Operation(summary = "Listar productos con stock mayor", description = "Retorna los productos cuyo stock actual es mayor al valor indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos sobre el stock indicado", content = @Content)
    })
    @GetMapping("/stock-mayor/{stockActual}")
    public CollectionModel<ProductoResponse> findByStockActualGreaterThan(@PathVariable Integer stockActual) {
        List<ProductoResponse> list = productoService.findByStockActualGreaterThan(stockActual);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ProductoController.class).findByStockActualGreaterThan(stockActual)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponse create(@Valid @RequestBody ProductoRequest request) {
        return addLinks(productoService.create(request));
    }

    @PutMapping("/{id}")
    public ProductoResponse update(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        return addLinks(productoService.update(id, request));
    }

    @PatchMapping("/{id}/stock")
    public ProductoResponse ajustarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        return addLinks(productoService.ajustarStock(id, cantidad));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        productoService.deleteById(id);
    }
}
