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

import cl.hilton.pagos.dto.ProjReservaRequest;
import cl.hilton.pagos.dto.ProjReservaResponse;
import cl.hilton.pagos.service.ProjReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pagos/reservas")
@RequiredArgsConstructor
public class ProjReservaController {

    private final ProjReservaService reservaService;

    @GetMapping
    public List<ProjReservaResponse> findAll() {
        return reservaService.findAll();
    }

    @GetMapping("/codigo/{codigoReserva}")
    public ProjReservaResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return reservaService.findByCodigoReserva(codigoReserva);
    }

    @GetMapping("/huesped/{emailHuesped}")
    public List<ProjReservaResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        return reservaService.findByEmailHuesped(emailHuesped);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<ProjReservaResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return reservaService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/fecha-entrada/{fechaEntrada}")
    public List<ProjReservaResponse> findByFechaEntrada(@PathVariable LocalDate fechaEntrada) {
        return reservaService.findByFechaEntrada(fechaEntrada);
    }

    @GetMapping("/fecha-salida/{fechaSalida}")
    public List<ProjReservaResponse> findByFechaSalida(@PathVariable LocalDate fechaSalida) {
        return reservaService.findByFechaSalida(fechaSalida);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjReservaResponse create(@Valid @RequestBody ProjReservaRequest request) {
        return reservaService.create(request);
    }

    @PostMapping("/sincronizar/codigo/{codigoReserva}")
    public ProjReservaResponse sincronizarPorCodigoReserva(@PathVariable String codigoReserva) {
        return reservaService.sincronizarPorCodigoReserva(codigoReserva);
    }

    @PutMapping("/codigo/{codigoReserva}")
    public ProjReservaResponse update(
            @PathVariable String codigoReserva,
            @Valid @RequestBody ProjReservaRequest request) {
        return reservaService.update(codigoReserva, request);
    }

    @DeleteMapping("/codigo/{codigoReserva}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCodigoReserva(@PathVariable String codigoReserva) {
        reservaService.deleteByCodigoReserva(codigoReserva);
    }
}
