package cl.hilton.autenticacion.controller;

import java.util.List;

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

import cl.hilton.autenticacion.dto.SesionRequest;
import cl.hilton.autenticacion.dto.SesionResponse;
import cl.hilton.autenticacion.service.SesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sesiones")
@RequiredArgsConstructor
public class SesionController {

    private final SesionService sesionService;

    @GetMapping
    public List<SesionResponse> findAll() {
        return sesionService.findAll();
    }

    @GetMapping("/{id}")
    public SesionResponse findById(@PathVariable Long id) {
        return sesionService.findById(id);
    }

    @GetMapping("/token/{tokenHash}")
    public SesionResponse findByTokenHash(@PathVariable String tokenHash) {
        return sesionService.findByTokenHash(tokenHash);
    }

    @GetMapping("/usuario/{usuarioEmail}")
    public SesionResponse findByUsuarioEmail(@PathVariable String usuarioEmail) {
        return sesionService.findByUsuarioEmail(usuarioEmail);
    }

    @GetMapping("/invalidada/{invalidada}")
    public List<SesionResponse> findByInvalidada(@PathVariable Boolean invalidada) {
        return sesionService.findByInvalidada(invalidada);
    }

    @GetMapping("/activas")
    public List<SesionResponse> findActivas() {
        return sesionService.findActivas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SesionResponse create(@Valid @RequestBody SesionRequest request) {
        return sesionService.create(request);
    }

    @PutMapping("/{id}")
    public SesionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody SesionRequest request) {
        return sesionService.update(id, request);
    }

    @PutMapping("/{id}/invalidar")
    public SesionResponse invalidar(@PathVariable Long id) {
        return sesionService.invalidar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        sesionService.deleteById(id);
    }
}
