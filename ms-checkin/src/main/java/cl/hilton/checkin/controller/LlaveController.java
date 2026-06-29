package cl.hilton.checkin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.checkin.dto.LlaveRequest;
import cl.hilton.checkin.dto.LlaveResponse;
import cl.hilton.checkin.service.LlaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/llaves")
@RequiredArgsConstructor
public class LlaveController {

    private final LlaveService llaveService;

    @GetMapping
    public List<LlaveResponse> findAll() {
        return llaveService.findAll();
    }

    @GetMapping("/{id}")
    public LlaveResponse findById(@PathVariable Long id) {
        return llaveService.findById(id);
    }

    @GetMapping("/codigo/{codigoLlave}")
    public LlaveResponse findByCodigoLlave(@PathVariable String codigoLlave) {
        return llaveService.findByCodigoLlave(codigoLlave);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<LlaveResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return llaveService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/activa/{activa}")
    public List<LlaveResponse> findByActiva(@PathVariable Boolean activa) {
        return llaveService.findByActiva(activa);
    }

    @GetMapping("/reserva/{codigoReserva}")
    public List<LlaveResponse> findByCodigoReserva(@PathVariable String codigoReserva) {
        return llaveService.findByCodigoReserva(codigoReserva);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LlaveResponse create(@Valid @RequestBody LlaveRequest request) {
        return llaveService.create(request);
    }

    @PutMapping("/{id}")
    public LlaveResponse update(@PathVariable Long id, @Valid @RequestBody LlaveRequest request) {
        return llaveService.update(id, request);
    }

    @PatchMapping("/{id}/estado")
    public LlaveResponse updateEstado(@PathVariable Long id, @RequestParam Boolean activa) {
        return llaveService.updateEstado(id, activa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        llaveService.deleteById(id);
    }
}