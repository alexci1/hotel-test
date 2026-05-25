package cl.hilton.checkin.controller;

import cl.hilton.checkin.dto.CheckinRequest;
import cl.hilton.checkin.dto.CheckinResponse;
import cl.hilton.checkin.service.CheckinService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkins")
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @GetMapping
    public List<CheckinResponse> listar() {
        return checkinService.listar();
    }

    @GetMapping("/{id}")
    public CheckinResponse buscarPorId(@PathVariable Long id) {
        return checkinService.buscarPorId(id);
    }

    @GetMapping("/reserva/{codigoReserva}")
    public CheckinResponse buscarPorReserva(@PathVariable String codigoReserva) {
        return checkinService.buscarPorReserva(codigoReserva);
    }

    @GetMapping("/huesped/{emailHuesped}")
    public List<CheckinResponse> buscarPorHuesped(@PathVariable String emailHuesped) {
        return checkinService.buscarPorHuesped(emailHuesped);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckinResponse crear(@Valid @RequestBody CheckinRequest request) {
        return checkinService.crear(request);
    }

    @PutMapping("/{id}")
    public CheckinResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CheckinRequest request
    ) {
        return checkinService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        checkinService.eliminar(id);
    }
}