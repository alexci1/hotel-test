package cl.hilton.notificaciones.controller;

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

import cl.hilton.notificaciones.dto.EnvioRequest;
import cl.hilton.notificaciones.dto.EnvioResponse;
import cl.hilton.notificaciones.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/envios")
@RequiredArgsConstructor
public class EnvioController {

    private final EnvioService envioService;
    private static final String RUTA_RELACIONADA = "/noti" + "ficacion/{notificacionId}";

    private EnvioResponse addLinks(EnvioResponse e) {
        e.add(linkTo(methodOn(EnvioController.class).findById(e.getId())).withSelfRel());
        e.add(linkTo(methodOn(EnvioController.class).update(e.getId(), null)).withRel("update"));
        e.add(linkTo(methodOn(EnvioController.class).findById(e.getId())).withRel("delete"));
        e.add(linkTo(methodOn(EnvioController.class).findAll()).withRel("all"));
        return e;
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiRespon\u0073e(responseCode = "200", description = "OK")
    @ApiRespon\u0073e(responseCode = "404", description = "No encontrado")
    @GetMapping
    public CollectionModel<EnvioResponse> findAll() {
        List<EnvioResponse> list = envioService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(EnvioController.class).findAll()).withSelfRel());
    }

    @Oper\u0061tion(summary = "Obtener registro", description = "Obtiene registro", responses = {
        @ApiRespon\u0073e(responseCode = "200", description = "OK"),
        @ApiRespon\u0073e(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/{id}")
    public EnvioResponse findById(@PathVariable Long id) {
        return addLinks(envioService.findById(id));
    }

    @Oper\u0061tion(summary = "Obtener registro", description = "Obtiene registro")
    @GetMapping(RUTA_RELACIONADA)
    public EnvioResponse findByNotificacionId(@PathVariable Long notificacionId) {
        return addLinks(envioService.findByNotificacionId(notificacionId));
    }

    @Oper\u0061tion(summary = "Listar registros", description = "Lista registros")
    @GetMapping("/estado/{estado}")
    public CollectionModel<EnvioResponse> findByEstado(@PathVariable String estado) {
        List<EnvioResponse> list = envioService.findByEstado(estado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(EnvioController.class).findByEstado(estado)).withSelfRel());
    }

    @Oper\u0061tion(summary = "Listar registros", description = "Lista registros")
    @GetMapping("/fecha/{enviadoEn}")
    public CollectionModel<EnvioResponse> findByEnviadoEn(@PathVariable LocalDate enviadoEn) {
        List<EnvioResponse> list = envioService.findByEnviadoEn(enviadoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(EnvioController.class).findByEnviadoEn(enviadoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnvioResponse create(@Valid @RequestBody EnvioRequest request) {
        return addLinks(envioService.create(request));
    }

    @PutMapping("/{id}")
    public EnvioResponse update(
            @PathVariable Long id,
            @Valid @RequestBody EnvioRequest request) {
        return addLinks(envioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        envioService.deleteById(id);
    }
}
