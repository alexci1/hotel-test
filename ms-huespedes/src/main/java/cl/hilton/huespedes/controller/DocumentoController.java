package cl.hilton.huespedes.controller;

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

import cl.hilton.huespedes.dto.DocumentoRequest;
import cl.hilton.huespedes.dto.DocumentoResponse;
import cl.hilton.huespedes.service.DocumentoService;
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
@RequestMapping("/api/v1/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;

    private DocumentoResponse addLinks(DocumentoResponse d) {
        d.add(linkTo(methodOn(DocumentoController.class).findById(d.getId())).withSelfRel());
        d.add(linkTo(methodOn(DocumentoController.class).update(d.getId(), null)).withRel("update"));
        d.add(linkTo(DocumentoController.class).slash(d.getId()).withRel("delete"));
        d.add(linkTo(methodOn(DocumentoController.class).findAll()).withRel("all"));
        return d;
    }

    @Operation(summary = "Listar documentos", description = "Retorna todos los documentos registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documentos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron documentos", content = @Content)
    })
    @GetMapping
    public CollectionModel<DocumentoResponse> findAll() {
        List<DocumentoResponse> list = documentoService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DocumentoController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener documento por ID", description = "Retorna un documento según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento encontrado",
            content = @Content(schema = @Schema(implementation = DocumentoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Documento no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public DocumentoResponse findById(@PathVariable Long id) {
        return addLinks(documentoService.findById(id));
    }

    @Operation(summary = "Listar documentos por huésped", description = "Retorna los documentos asociados al email de un huésped")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documentos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron documentos para el huésped indicado", content = @Content)
    })
    @GetMapping("/huesped/{emailHuesped}")
    public CollectionModel<DocumentoResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        List<DocumentoResponse> list = documentoService.findByEmailHuesped(emailHuesped);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DocumentoController.class).findByEmailHuesped(emailHuesped)).withSelfRel());
    }

    @Operation(summary = "Listar documentos por tipo", description = "Retorna los documentos filtrados por tipo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documentos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron documentos del tipo indicado", content = @Content)
    })
    @GetMapping("/tipo/{tipo}")
    public CollectionModel<DocumentoResponse> findByTipo(@PathVariable String tipo) {
        List<DocumentoResponse> list = documentoService.findByTipo(tipo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DocumentoController.class).findByTipo(tipo)).withSelfRel());
    }

    @Operation(summary = "Listar documentos por país emisor", description = "Retorna los documentos asociados a un país emisor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documentos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron documentos para el país indicado", content = @Content)
    })
    @GetMapping("/pais/{paisEmisor}")
    public CollectionModel<DocumentoResponse> findByPaisEmisor(@PathVariable String paisEmisor) {
        List<DocumentoResponse> list = documentoService.findByPaisEmisor(paisEmisor);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DocumentoController.class).findByPaisEmisor(paisEmisor)).withSelfRel());
    }

    @Operation(summary = "Listar documentos por vencimiento", description = "Retorna los documentos filtrados por fecha de vencimiento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documentos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron documentos para la fecha indicada", content = @Content)
    })
    @GetMapping("/vencimiento/{vencimiento}")
    public CollectionModel<DocumentoResponse> findByVencimiento(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vencimiento) {
        List<DocumentoResponse> list = documentoService.findByVencimiento(vencimiento);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DocumentoController.class).findByVencimiento(vencimiento)).withSelfRel());
    }

    @Operation(summary = "Obtener documento por tipo, número y país", description = "Retorna un documento según tipo, número y país emisor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento encontrado",
            content = @Content(schema = @Schema(implementation = DocumentoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Documento no encontrado", content = @Content)
    })
    @GetMapping("/tipo/{tipo}/numero/{numero}/pais/{paisEmisor}")
    public DocumentoResponse findByTipoNumeroPais(
            @PathVariable String tipo,
            @PathVariable String numero,
            @PathVariable String paisEmisor) {
        return addLinks(documentoService.findByTipoNumeroPais(tipo, numero, paisEmisor));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentoResponse create(@Valid @RequestBody DocumentoRequest request) {
        return addLinks(documentoService.create(request));
    }

    @PutMapping("/{id}")
    public DocumentoResponse update(@PathVariable Long id, @Valid @RequestBody DocumentoRequest request) {
        return addLinks(documentoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        documentoService.deleteById(id);
    }
}
