package cl.hilton.huespedes.controller;

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

import cl.hilton.huespedes.dto.PreferenciaRequest;
import cl.hilton.huespedes.dto.PreferenciaResponse;
import cl.hilton.huespedes.service.PreferenciaService;
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
@RequestMapping("/api/v1/preferencias")
@RequiredArgsConstructor
public class PreferenciaController {

    private final PreferenciaService preferenciaService;

    private PreferenciaResponse addLinks(PreferenciaResponse p) {
        p.add(linkTo(methodOn(PreferenciaController.class).findById(p.getId())).withSelfRel());
        p.add(linkTo(methodOn(PreferenciaController.class).update(p.getId(), null)).withRel("update"));
        p.add(linkTo(PreferenciaController.class).slash(p.getId()).withRel("delete"));
        p.add(linkTo(methodOn(PreferenciaController.class).findAll()).withRel("all"));
        return p;
    }

    @Operation(summary = "Listar preferencias", description = "Retorna todas las preferencias registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Preferencias encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PreferenciaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron preferencias", content = @Content)
    })
    @GetMapping
    public CollectionModel<PreferenciaResponse> findAll() {
        List<PreferenciaResponse> list = preferenciaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PreferenciaController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener preferencia por ID", description = "Retorna una preferencia según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Preferencia encontrada",
            content = @Content(schema = @Schema(implementation = PreferenciaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Preferencia no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public PreferenciaResponse findById(@PathVariable Long id) {
        return addLinks(preferenciaService.findById(id));
    }

    @Operation(summary = "Obtener preferencia por huésped", description = "Retorna una preferencia asociada al email de un huésped")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Preferencia encontrada",
            content = @Content(schema = @Schema(implementation = PreferenciaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Preferencia no encontrada para el huésped indicado", content = @Content)
    })
    @GetMapping("/huesped/{emailHuesped}")
    public PreferenciaResponse findByEmailHuesped(@PathVariable String emailHuesped) {
        return addLinks(preferenciaService.findByEmailHuesped(emailHuesped));
    }

    @Operation(summary = "Listar preferencias por tipo de cama", description = "Retorna las preferencias asociadas a un tipo de cama")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Preferencias encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PreferenciaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron preferencias para el tipo indicado", content = @Content)
    })
    @GetMapping("/tipo-cama/{tipoCama}")
    public CollectionModel<PreferenciaResponse> findByTipoCama(@PathVariable String tipoCama) {
        List<PreferenciaResponse> list = preferenciaService.findByTipoCama(tipoCama);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PreferenciaController.class).findByTipoCama(tipoCama)).withSelfRel());
    }

    @Operation(summary = "Listar preferencias por piso", description = "Retorna las preferencias asociadas a un piso")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Preferencias encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PreferenciaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron preferencias para el piso indicado", content = @Content)
    })
    @GetMapping("/piso/{pisoPreferido}")
    public CollectionModel<PreferenciaResponse> findByPisoPreferido(@PathVariable Integer pisoPreferido) {
        List<PreferenciaResponse> list = preferenciaService.findByPisoPreferido(pisoPreferido);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PreferenciaController.class).findByPisoPreferido(pisoPreferido)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PreferenciaResponse create(@Valid @RequestBody PreferenciaRequest request) {
        return addLinks(preferenciaService.create(request));
    }

    @PutMapping("/{id}")
    public PreferenciaResponse update(@PathVariable Long id, @Valid @RequestBody PreferenciaRequest request) {
        return addLinks(preferenciaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        preferenciaService.deleteById(id);
    }
}
