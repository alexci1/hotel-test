package cl.hilton.checkin.controller;

import cl.hilton.checkin.dto.CheckoutRequest;
import cl.hilton.checkin.dto.CheckoutResponse;
import cl.hilton.checkin.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkouts")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping
    public List<CheckoutResponse> listar() {
        return checkoutService.listar();
    }

    @GetMapping("/{id}")
    public CheckoutResponse buscarPorId(@PathVariable Long id) {
        return checkoutService.buscarPorId(id);
    }

    @GetMapping("/reserva/{codigoReserva}")
    public CheckoutResponse buscarPorReserva(@PathVariable String codigoReserva) {
        return checkoutService.buscarPorReserva(codigoReserva);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckoutResponse crear(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.crear(request);
    }

    @PutMapping("/{id}")
    public CheckoutResponse actualizar(@PathVariable Long id, @Valid @RequestBody CheckoutRequest request) {
        return checkoutService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        checkoutService.eliminar(id);
    }
}