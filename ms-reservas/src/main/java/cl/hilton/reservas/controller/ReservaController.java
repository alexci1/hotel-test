package cl.hilton.reservas.controller;

import cl.hilton.reservas.model.Reserva;
import cl.hilton.reservas.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public List<Reserva> obtenerReservas() {
        return reservaService.obtenerReservas();
    }

    @GetMapping("/{id}")
    public Optional<Reserva> obtenerPorId(@PathVariable Long id) {
        return reservaService.obtenerPorId(id);
    }

    @GetMapping("/codigo/{codigoReserva}")
    public Optional<Reserva> obtenerPorCodigo(@PathVariable String codigoReserva) {
        return reservaService.obtenerPorCodigo(codigoReserva);
    }

    @PostMapping
    public Reserva guardarReserva(@RequestBody Reserva reserva) {
        return reservaService.guardarReserva(reserva);
    }

    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
    }

    @GetMapping("/estado/{estado}")
    public List<Reserva> obtenerPorEstado(@PathVariable String estado) {
        return reservaService.obtenerPorEstado(estado);
    }
}