package cl.hilton.huespedes.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import cl.hilton.huespedes.dto.HuespedRequest;
import cl.hilton.huespedes.dto.HuespedResponse;
import cl.hilton.huespedes.service.HuespedService;
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
@RequestMapping("/api/v1/huespedes")
@RequiredArgsConstructor
public class HuespedController {

    private final HuespedService huespedService;

    private HuespedResponse addLinks(HuespedResponse h) {
        h.add(linkTo(methodOn(HuespedController.class).findById(h.getId())).withSelfRel());
        h.add(linkTo(methodOn(HuespedController.class).update(h.getId(), null)).withRel("update"));
        h.add(linkTo(HuespedController.class).slash(h.getId()).withRel("delete"));
        h.add(linkTo(methodOn(HuespedController.class).cambiarActivo(h.getId(), null)).withRel("cambiar-activo"));
        h.add(linkTo(methodOn(HuespedController.class).findAll()).withRel("all"));
        return h;
    }

    @Operation(summary = "Listar huéspedes", description = "Retorna todos los huéspedes registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Huéspedes encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HuespedResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron huéspedes", content = @Content)
    })
    @GetMapping
    public CollectionModel<HuespedResponse> findAll() {
        List<HuespedResponse> list = huespedService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HuespedController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener huésped por ID", description = "Retorna un huésped según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Huésped encontrado",
            content = @Content(schema = @Schema(implementation = HuespedResponse.class))),
        @ApiResponse(responseCode = "404", description = "Huésped no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public HuespedResponse findById(@PathVariable Long id) {
        return addLinks(huespedService.findById(id));
    }

    @Operation(summary = "Obtener huésped por email", description = "Retorna un huésped según su correo electrónico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Huésped encontrado",
            content = @Content(schema = @Schema(implementation = HuespedResponse.class))),
        @ApiResponse(responseCode = "404", description = "Huésped no encontrado", content = @Content)
    })
    @GetMapping("/email/{email}")
    public HuespedResponse findByEmail(@PathVariable String email) {
        return addLinks(huespedService.findByEmail(email));
    }

    @Operation(summary = "Listar huéspedes por nombre", description = "Retorna los huéspedes que coinciden con el nombre completo indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Huéspedes encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HuespedResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron huéspedes con el nombre indicado", content = @Content)
    })
    @GetMapping("/nombre/{nombreCompleto}")
    public CollectionModel<HuespedResponse> findByNombreCompleto(@PathVariable String nombreCompleto) {
        List<HuespedResponse> list = huespedService.findByNombreCompleto(nombreCompleto);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HuespedController.class).findByNombreCompleto(nombreCompleto)).withSelfRel());
    }

    @Operation(summary = "Listar huéspedes por estado activo", description = "Retorna los huéspedes filtrados por su estado activo o inactivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Huéspedes encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HuespedResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron huéspedes con el estado indicado", content = @Content)
    })
    @GetMapping("/activo/{activo}")
    public CollectionModel<HuespedResponse> findByActivo(@PathVariable Boolean activo) {
        List<HuespedResponse> list = huespedService.findByActivo(activo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HuespedController.class).findByActivo(activo)).withSelfRel());
    }

    @Operation(summary = "Listar huéspedes por fecha de creación", description = "Retorna los huéspedes creados en una fecha específica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Huéspedes encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HuespedResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron huéspedes para la fecha indicada", content = @Content)
    })
    @GetMapping("/creado/{creadoEn}")
    public CollectionModel<HuespedResponse> findByCreadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creadoEn) {
        List<HuespedResponse> list = huespedService.findByCreadoEn(creadoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HuespedController.class).findByCreadoEn(creadoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HuespedResponse create(@Valid @RequestBody HuespedRequest request) {
        return addLinks(huespedService.create(request));
    }

    @PutMapping("/{id}")
    public HuespedResponse update(@PathVariable Long id, @Valid @RequestBody HuespedRequest request) {
        return addLinks(huespedService.update(id, request));
    }

    @PatchMapping("/{id}/activo")
    public HuespedResponse cambiarActivo(@PathVariable Long id, @RequestParam Boolean activo) {
        return addLinks(huespedService.cambiarActivo(id, activo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        huespedService.deleteById(id);
    }
}
