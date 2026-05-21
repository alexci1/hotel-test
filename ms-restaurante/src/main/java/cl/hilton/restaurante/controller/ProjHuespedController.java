package cl.hilton.restaurante.controller;
import cl.hilton.restaurante.dto.ProjHuespedRequest;
import cl.hilton.restaurante.dto.ProjHuespedResponse;
import cl.hilton.restaurante.service.ProjHuespedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/proj-huespedes")
@RequiredArgsConstructor
public class ProjHuespedController {

    private final ProjHuespedService huespedService;

    @GetMapping
    public List<ProjHuespedResponse> listar() {
        return huespedService.listar();
    }

    @GetMapping("/{email}")
    public ProjHuespedResponse buscarPorEmail(@PathVariable String email) {
        return huespedService.buscarPorEmail(email);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<ProjHuespedResponse> buscarPorHabitacion(@PathVariable String numeroHabitacion) {
        return huespedService.buscarPorHabitacion(numeroHabitacion);
    }

    @GetMapping("/buscar")
    public List<ProjHuespedResponse> buscarPorNombre(@RequestParam String nombre) {
        return huespedService.buscarPorNombre(nombre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjHuespedResponse crear(@Valid @RequestBody ProjHuespedRequest request) {
        return huespedService.crear(request);
    }

    @PutMapping("/{email}")
    public ProjHuespedResponse actualizar(@PathVariable String email, @Valid @RequestBody ProjHuespedRequest request) {
        return huespedService.actualizar(email, request);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String email) {
        huespedService.eliminar(email);
    }
}