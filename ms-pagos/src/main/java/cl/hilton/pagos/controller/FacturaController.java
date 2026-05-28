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

import cl.hilton.pagos.dto.FacturaRequest;
import cl.hilton.pagos.dto.FacturaResponse;
import cl.hilton.pagos.service.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pagos/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping
    public List<FacturaResponse> findAll() {
        return facturaService.findAll();
    }

    @GetMapping("/{id}")
    public FacturaResponse findById(@PathVariable Long id) {
        return facturaService.findById(id);
    }

    @GetMapping("/numero/{numeroFactura}")
    public FacturaResponse findByNumeroFactura(@PathVariable String numeroFactura) {
        return facturaService.findByNumeroFactura(numeroFactura);
    }

    @GetMapping("/reserva/{codigoReserva}")
    public FacturaResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return facturaService.findByCodigoReserva(codigoReserva);
    }

    @GetMapping("/huesped/{emailHuesped}")
    public List<FacturaResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        return facturaService.findByEmailHuesped(emailHuesped);
    }

    @GetMapping("/estado/{estado}")
    public List<FacturaResponse> findByEstado(@PathVariable String estado) {
        return facturaService.findByEstado(estado);
    }

    @GetMapping("/fecha/{emitidaEn}")
    public List<FacturaResponse> findByEmitidaEn(@PathVariable LocalDate emitidaEn) {
        return facturaService.findByEmitidaEn(emitidaEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaResponse create(@Valid @RequestBody FacturaRequest request) {
        return facturaService.create(request);
    }

    @PutMapping("/{id}")
    public FacturaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody FacturaRequest request) {
        return facturaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        facturaService.deleteById(id);
    }
}
