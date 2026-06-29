package cl.hilton.tarifas.controller;

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

import cl.hilton.tarifas.dto.TarifaRequest;
import cl.hilton.tarifas.dto.TarifaResponse;
import cl.hilton.tarifas.service.TarifaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    private TarifaResponse addLinks(TarifaResponse t) {
        t.add(linkTo(methodOn(TarifaController.class).findById(t.getId())).withSelfRel());
        t.add(linkTo(methodOn(TarifaController.class).update(t.getId(), null)).withRel("update"));
        t.add(linkTo(methodOn(TarifaController.class).findAll()).withRel("all"));
        return t;
    }

    @GetMapping
    public CollectionModel<TarifaResponse> findAll() {
        List<TarifaResponse> list = tarifaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TarifaController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public TarifaResponse findById(@PathVariable Long id) {
        return addLinks(tarifaService.findById(id));
    }

    @GetMapping("/temporada/{codigoTemporada}/tipo/{tipoHabitacion}")
    public TarifaResponse findByTemporadaAndTipoHabitacion(
            @PathVariable String codigoTemporada,
            @PathVariable String tipoHabitacion) {
        return addLinks(tarifaService.findByTemporadaAndTipoHabitacion(codigoTemporada, tipoHabitacion));
    }

    @GetMapping("/temporada/{codigoTemporada}")
    public CollectionModel<TarifaResponse> findByCodigoTemporada(@PathVariable String codigoTemporada) {
        List<TarifaResponse> list = tarifaService.findByCodigoTemporada(codigoTemporada);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TarifaController.class).findByCodigoTemporada(codigoTemporada)).withSelfRel());
    }

    @GetMapping("/tipo/{tipoHabitacion}")
    public CollectionModel<TarifaResponse> findByTipoHabitacion(@PathVariable String tipoHabitacion) {
        List<TarifaResponse> list = tarifaService.findByTipoHabitacion(tipoHabitacion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TarifaController.class).findByTipoHabitacion(tipoHabitacion)).withSelfRel());
    }

    @GetMapping("/activa/{activa}")
    public CollectionModel<TarifaResponse> findByActiva(@PathVariable Boolean activa) {
        List<TarifaResponse> list = tarifaService.findByActiva(activa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TarifaController.class).findByActiva(activa)).withSelfRel());
    }

    @GetMapping("/desayuno/{incluyeDesayuno}")
    public CollectionModel<TarifaResponse> findByIncluyeDesayuno(@PathVariable Boolean incluyeDesayuno) {
        List<TarifaResponse> list = tarifaService.findByIncluyeDesayuno(incluyeDesayuno);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TarifaController.class).findByIncluyeDesayuno(incluyeDesayuno)).withSelfRel());
    }

    @GetMapping("/tipo/{tipoHabitacion}/activa/{activa}")
    public CollectionModel<TarifaResponse> findByTipoHabitacionAndActiva(
            @PathVariable String tipoHabitacion,
            @PathVariable Boolean activa) {
        List<TarifaResponse> list = tarifaService.findByTipoHabitacionAndActiva(tipoHabitacion, activa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TarifaController.class).findByTipoHabitacionAndActiva(tipoHabitacion, activa)).withSelfRel());
    }

    @GetMapping("/exists/tipo/{tipoHabitacion}/activa")
    public boolean existsTarifaActivaByTipoHabitacion(@PathVariable String tipoHabitacion) {
        return tarifaService.existsTarifaActivaByTipoHabitacion(tipoHabitacion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarifaResponse create(@Valid @RequestBody TarifaRequest request) {
        return addLinks(tarifaService.create(request));
    }

    @PutMapping("/{id}")
    public TarifaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TarifaRequest request) {
        return addLinks(tarifaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tarifaService.deleteById(id);
    }
}
