package cl.hilton.tarifas.controller;

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

import cl.hilton.tarifas.dto.DescuentoRequest;
import cl.hilton.tarifas.dto.DescuentoResponse;
import cl.hilton.tarifas.service.DescuentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/descuentos")
@RequiredArgsConstructor
public class DescuentoController {

    private final DescuentoService descuentoService;

    @GetMapping
    public List<DescuentoResponse> findAll() {
        return descuentoService.findAll();
    }

    @GetMapping("/{id}")
    public DescuentoResponse findById(@PathVariable Long id) {
        return descuentoService.findById(id);
    }

    @GetMapping("/codigo/{codigoDescuento}")
    public DescuentoResponse findByCodigoDescuento(@PathVariable String codigoDescuento) {
        return descuentoService.findByCodigoDescuento(codigoDescuento);
    }

    @GetMapping("/aplica-a/{aplicaA}")
    public List<DescuentoResponse> findByAplicaA(@PathVariable String aplicaA) {
        return descuentoService.findByAplicaA(aplicaA);
    }

    @GetMapping("/activo/{activo}")
    public List<DescuentoResponse> findByActivo(@PathVariable Boolean activo) {
        return descuentoService.findByActivo(activo);
    }

    @GetMapping("/valido-desde/{validoDesde}")
    public List<DescuentoResponse> findByValidoDesde(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validoDesde) {
        return descuentoService.findByValidoDesde(validoDesde);
    }

    @GetMapping("/valido-hasta/{validoHasta}")
    public List<DescuentoResponse> findByValidoHasta(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validoHasta) {
        return descuentoService.findByValidoHasta(validoHasta);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DescuentoResponse create(@Valid @RequestBody DescuentoRequest request) {
        return descuentoService.create(request);
    }

    @PutMapping("/{id}")
    public DescuentoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody DescuentoRequest request) {
        return descuentoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        descuentoService.deleteById(id);
    }
}
