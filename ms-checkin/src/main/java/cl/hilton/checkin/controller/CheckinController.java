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

import cl.hilton.checkin.dto.CheckinRequest;
import cl.hilton.checkin.dto.CheckinResponse;
import cl.hilton.checkin.service.CheckinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/checkins")
@RequiredArgsConstructor
public class CheckinController {

    private final CheckinService checkinService;

    @GetMapping
    public List<CheckinResponse> findAll() {
        return checkinService.findAll();
    }

    @GetMapping("/{id}")
    public CheckinResponse findById(@PathVariable Long id) {
        return checkinService.findById(id);
    }

    @GetMapping("/reserva/{codigoReserva}")
    public CheckinResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return checkinService.findByCodigoReserva(codigoReserva);
    }

    @GetMapping("/huesped/{emailHuesped}")
    public List<CheckinResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        return checkinService.findByEmailHuesped(emailHuesped);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<CheckinResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return checkinService.findByNumeroHabitacion(numeroHabitacion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckinResponse create(@Valid @RequestBody CheckinRequest request) {
        return checkinService.create(request);
    }

    @PutMapping("/{id}")
    public CheckinResponse update(@PathVariable Long id, @Valid @RequestBody CheckinRequest request) {
        return checkinService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        checkinService.deleteById(id);
    }
}