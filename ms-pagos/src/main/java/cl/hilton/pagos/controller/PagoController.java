package cl.hilton.pagos.controller;

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

import cl.hilton.pagos.dto.PagoRequest;
import cl.hilton.pagos.dto.PagoResponse;
import cl.hilton.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public List<PagoResponse> findAll() {
        return pagoService.findAll();
    }

    @GetMapping("/{id}")
    public PagoResponse findById(@PathVariable Long id) {
        return pagoService.findById(id);
    }

    @GetMapping("/factura/{numeroFactura}")
    public List<PagoResponse> findByNumeroFactura(@PathVariable String numeroFactura) {
        return pagoService.findByNumeroFactura(numeroFactura);
    }

    @GetMapping("/metodo/{metodo}")
    public List<PagoResponse> findByMetodo(@PathVariable String metodo) {
        return pagoService.findByMetodo(metodo);
    }

    @GetMapping("/fecha/{pagadoEn}")
    public List<PagoResponse> findByPagadoEn(@PathVariable LocalDate pagadoEn) {
        return pagoService.findByPagadoEn(pagadoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagoResponse create(@Valid @RequestBody PagoRequest request) {
        return pagoService.create(request);
    }

    @PutMapping("/{id}")
    public PagoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody PagoRequest request) {
        return pagoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        pagoService.deleteById(id);
    }
}
