package cl.hilton.reservas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import cl.hilton.reservas.dto.CancelacionRequest;
import cl.hilton.reservas.dto.CancelacionResponse;
import cl.hilton.reservas.service.CancelacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cancelaciones")
@RequiredArgsConstructor
public class CancelacionController {

    private final CancelacionService cancelacionService;

    @GetMapping
    public List<CancelacionResponse> findAll() {
        return cancelacionService.findAll();
    }

    @GetMapping("/{id}")
    public CancelacionResponse findById(@PathVariable Long id) {
        return cancelacionService.findById(id);
    }

    @GetMapping("/reserva/{codigoReserva}")
    public CancelacionResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return cancelacionService.findByCodigoReserva(codigoReserva);
    }

    @GetMapping("/fecha/{canceladoEn}")
    public List<CancelacionResponse> findByCanceladoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate canceladoEn) {
        return cancelacionService.findByCanceladoEn(canceladoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CancelacionResponse create(@Valid @RequestBody CancelacionRequest request) {
        return cancelacionService.create(request);
    }

    @PutMapping("/{id}")
    public CancelacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CancelacionRequest request) {
        return cancelacionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        cancelacionService.deleteById(id);
    }
}
