package cl.hilton.notificaciones.controller;

import java.time.LocalDate;
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

import cl.hilton.notificaciones.dto.EnvioRequest;
import cl.hilton.notificaciones.dto.EnvioResponse;
import cl.hilton.notificaciones.service.EnvioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notificaciones/envios")
@RequiredArgsConstructor
public class EnvioController {

    private final EnvioService envioService;

    @GetMapping
    public List<EnvioResponse> findAll() {
        return envioService.findAll();
    }

    @GetMapping("/{id}")
    public EnvioResponse findById(@PathVariable Long id) {
        return envioService.findById(id);
    }

    @GetMapping("/notificacion/{notificacionId}")
    public EnvioResponse findByNotificacionId(@PathVariable Long notificacionId) {
        return envioService.findByNotificacionId(notificacionId);
    }

    @GetMapping("/estado/{estado}")
    public List<EnvioResponse> findByEstado(@PathVariable String estado) {
        return envioService.findByEstado(estado);
    }

    @GetMapping("/fecha/{enviadoEn}")
    public List<EnvioResponse> findByEnviadoEn(@PathVariable LocalDate enviadoEn) {
        return envioService.findByEnviadoEn(enviadoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnvioResponse create(@Valid @RequestBody EnvioRequest request) {
        return envioService.create(request);
    }

    @PutMapping("/{id}")
    public EnvioResponse update(
            @PathVariable Long id,
            @Valid @RequestBody EnvioRequest request) {
        return envioService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        envioService.deleteById(id);
    }
}
