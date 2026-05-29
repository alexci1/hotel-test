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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.reservas.dto.ReservaRequest;
import cl.hilton.reservas.dto.ReservaResponse;
import cl.hilton.reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public List<ReservaResponse> findAll() {
        return reservaService.findAll();
    }

    @GetMapping("/{id}")
    public ReservaResponse findById(@PathVariable Long id) {
        return reservaService.findById(id);
    }

    @GetMapping("/codigo/{codigoReserva}")
    public ReservaResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return reservaService.findByCodigoReserva(codigoReserva);
    }

    @GetMapping("/huesped/{emailHuesped}")
    public List<ReservaResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        return reservaService.findByEmailHuesped(emailHuesped);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<ReservaResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return reservaService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/estado/{estado}")
    public List<ReservaResponse> findByEstado(@PathVariable String estado) {
        return reservaService.findByEstado(estado);
    }

    @GetMapping("/fecha-entrada/{fechaEntrada}")
    public List<ReservaResponse> findByFechaEntrada(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada) {
        return reservaService.findByFechaEntrada(fechaEntrada);
    }

    @GetMapping("/fecha-salida/{fechaSalida}")
    public List<ReservaResponse> findByFechaSalida(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida) {
        return reservaService.findByFechaSalida(fechaSalida);
    }

    @GetMapping("/rango-entrada")
    public List<ReservaResponse> findByRangoEntrada(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return reservaService.findByRangoEntrada(desde, hasta);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponse create(@Valid @RequestBody ReservaRequest request) {
        return reservaService.create(request);
    }

    @PutMapping("/{id}")
    public ReservaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequest request) {
        return reservaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reservaService.deleteById(id);
    }
}
