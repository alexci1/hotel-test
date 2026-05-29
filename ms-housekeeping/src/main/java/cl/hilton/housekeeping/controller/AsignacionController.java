package cl.hilton.housekeeping.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import cl.hilton.housekeeping.dto.AsignacionRequest;
import cl.hilton.housekeeping.dto.AsignacionResponse;
import cl.hilton.housekeeping.service.AsignacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/housekeeping/asignaciones")
@RequiredArgsConstructor
public class AsignacionController {

    private final AsignacionService asignacionService;

    @GetMapping
    public List<AsignacionResponse> findAll() {
        return asignacionService.findAll();
    }

    @GetMapping("/{id}")
    public AsignacionResponse findById(@PathVariable Long id) {
        return asignacionService.findById(id);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<AsignacionResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return asignacionService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/tarea/{codigoTarea}")
    public List<AsignacionResponse> findByCodigoTarea(@PathVariable String codigoTarea) {
        return asignacionService.findByCodigoTarea(codigoTarea);
    }

    @GetMapping("/camarero/{emailCamarero}")
    public List<AsignacionResponse> findByEmailCamarero(@PathVariable String emailCamarero) {
        return asignacionService.findByEmailCamarero(emailCamarero);
    }

    @GetMapping("/fecha/{fechaProgramada}")
    public List<AsignacionResponse> findByFechaProgramada(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaProgramada
    ) {
        return asignacionService.findByFechaProgramada(fechaProgramada);
    }

    @GetMapping("/estado/{estado}")
    public List<AsignacionResponse> findByEstado(@PathVariable String estado) {
        return asignacionService.findByEstado(estado);
    }

    @GetMapping("/prioridad/{prioridad}")
    public List<AsignacionResponse> findByPrioridad(@PathVariable Integer prioridad) {
        return asignacionService.findByPrioridad(prioridad);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AsignacionResponse create(@Valid @RequestBody AsignacionRequest request) {
        return asignacionService.create(request);
    }

    @PutMapping("/{id}")
    public AsignacionResponse update(@PathVariable Long id, @Valid @RequestBody AsignacionRequest request) {
        return asignacionService.update(id, request);
    }

    @PatchMapping("/{id}/estado")
    public AsignacionResponse updateEstado(@PathVariable Long id, @RequestParam String estado) {
        return asignacionService.updateEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        asignacionService.deleteById(id);
    }
}