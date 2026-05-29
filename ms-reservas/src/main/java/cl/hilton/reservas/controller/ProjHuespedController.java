package cl.hilton.reservas.controller;

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

import cl.hilton.reservas.dto.ProjHuespedRequest;
import cl.hilton.reservas.dto.ProjHuespedResponse;
import cl.hilton.reservas.service.ProjHuespedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservas/huespedes")
@RequiredArgsConstructor
public class ProjHuespedController {

    private final ProjHuespedService huespedService;

    @GetMapping
    public List<ProjHuespedResponse> findAll() {
        return huespedService.findAll();
    }

    @GetMapping("/email/{email}")
    public ProjHuespedResponse findByEmail(@PathVariable String email) {
        return huespedService.findByEmail(email);
    }

    @GetMapping("/nombre/{nombreCompleto}")
    public List<ProjHuespedResponse> findByNombreCompleto(@PathVariable String nombreCompleto) {
        return huespedService.findByNombreCompleto(nombreCompleto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjHuespedResponse create(@Valid @RequestBody ProjHuespedRequest request) {
        return huespedService.create(request);
    }

    @PostMapping("/sincronizar/email/{email}")
    public ProjHuespedResponse sincronizarPorEmail(@PathVariable String email) {
        return huespedService.sincronizarPorEmail(email);
    }

    @PutMapping("/email/{email}")
    public ProjHuespedResponse update(
            @PathVariable String email,
            @Valid @RequestBody ProjHuespedRequest request) {
        return huespedService.update(email, request);
    }

    @DeleteMapping("/email/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByEmail(@PathVariable String email) {
        huespedService.deleteByEmail(email);
    }
}
