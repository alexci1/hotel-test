package cl.hilton.housekeeping.controller;

import cl.hilton.housekeeping.dto.AsignacionRequest;
import cl.hilton.housekeeping.dto.AsignacionResponse;
import cl.hilton.housekeeping.service.AsignacionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    private final AsignacionService asignacionService;

    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @GetMapping
    public List<AsignacionResponse> listar() {
        return asignacionService.listar();
    }

    @GetMapping("/{id}")
    public AsignacionResponse buscarPorId(@PathVariable Long id) {
        return asignacionService.buscarPorId(id);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<AsignacionResponse> buscarPorHabitacion(@PathVariable String numeroHabitacion) {
        return asignacionService.buscarPorHabitacion(numeroHabitacion);
    }

    @GetMapping("/tarea/{codigoTarea}")
    public List<AsignacionResponse> buscarPorTarea(@PathVariable String codigoTarea) {
        return asignacionService.buscarPorTarea(codigoTarea);
    }

    @GetMapping("/camarero/{emailCamarero}")
    public List<AsignacionResponse> buscarPorCamarero(@PathVariable String emailCamarero) {
        return asignacionService.buscarPorCamarero(emailCamarero);
    }

    @GetMapping("/fecha/{fechaProgramada}")
    public List<AsignacionResponse> buscarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaProgramada
    ) {
        return asignacionService.buscarPorFecha(fechaProgramada);
    }

    @GetMapping("/estado/{estado}")
    public List<AsignacionResponse> buscarPorEstado(@PathVariable String estado) {
        return asignacionService.buscarPorEstado(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AsignacionResponse crear(@Valid @RequestBody AsignacionRequest request) {
        return asignacionService.crear(request);
    }

    @PutMapping("/{id}")
    public AsignacionResponse actualizar(@PathVariable Long id, @Valid @RequestBody AsignacionRequest request) {
        return asignacionService.actualizar(id, request);
    }

    @PatchMapping("/{id}/estado")
    public AsignacionResponse cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return asignacionService.cambiarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        asignacionService.eliminar(id);
    }
}