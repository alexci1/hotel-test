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

    @GetMapping
    public CollectionModel<PreferenciaResponse> findAll() {
        List<PreferenciaResponse> list = preferenciaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PreferenciaController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public PreferenciaResponse findById(@PathVariable Long id) {
        return addLinks(preferenciaService.findById(id));
    }

    @GetMapping("/huesped/{emailHuesped}")
    public PreferenciaResponse findByEmailHuesped(@PathVariable String emailHuesped) {
        return addLinks(preferenciaService.findByEmailHuesped(emailHuesped));
    }

    @GetMapping("/tipo-cama/{tipoCama}")
    public CollectionModel<PreferenciaResponse> findByTipoCama(@PathVariable String tipoCama) {
        List<PreferenciaResponse> list = preferenciaService.findByTipoCama(tipoCama);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PreferenciaController.class).findByTipoCama(tipoCama)).withSelfRel());
    }

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
