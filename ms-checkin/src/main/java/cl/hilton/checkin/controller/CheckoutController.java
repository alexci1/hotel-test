package cl.hilton.checkin.controller;

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

import cl.hilton.checkin.dto.CheckoutRequest;
import cl.hilton.checkin.dto.CheckoutResponse;
import cl.hilton.checkin.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/checkouts")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @GetMapping
    public List<CheckoutResponse> findAll() {
        return checkoutService.findAll();
    }

    @GetMapping("/{id}")
    public CheckoutResponse findById(@PathVariable Long id) {
        return checkoutService.findById(id);
    }

    @GetMapping("/reserva/{codigoReserva}")
    public CheckoutResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return checkoutService.findByCodigoReserva(codigoReserva);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckoutResponse create(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.create(request);
    }

    @PutMapping("/{id}")
    public CheckoutResponse update(@PathVariable Long id, @Valid @RequestBody CheckoutRequest request) {
        return checkoutService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        checkoutService.deleteById(id);
    }
}