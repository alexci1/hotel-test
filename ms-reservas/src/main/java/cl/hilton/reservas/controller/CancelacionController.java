package cl.hilton.reservas.controller;

import cl.hilton.reservas.model.Cancelacion;
import cl.hilton.reservas.service.CancelacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cancelaciones")
@RequiredArgsConstructor
public class CancelacionController {

    private final CancelacionService cancelacionService;

    @GetMapping
    public List<Cancelacion> obtenerCancelaciones() {
        return cancelacionService.obtenerCancelaciones();
    }

    @GetMapping("/{id}")
    public Optional<Cancelacion> obtenerPorId(@PathVariable Long id) {
        return cancelacionService.obtenerPorId(id);
    }

    @GetMapping("/reserva/{codigoReserva}")
    public Optional<Cancelacion> obtenerPorCodigoReserva(@PathVariable String codigoReserva) {
        return cancelacionService.obtenerPorCodigoReserva(codigoReserva);
    }

    @PostMapping
    public Cancelacion guardarCancelacion(@RequestBody Cancelacion cancelacion) {
        return cancelacionService.guardarCancelacion(cancelacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarCancelacion(@PathVariable Long id) {
        cancelacionService.eliminarCancelacion(id);
    }
}