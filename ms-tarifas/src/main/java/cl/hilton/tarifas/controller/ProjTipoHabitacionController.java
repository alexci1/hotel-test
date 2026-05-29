package cl.hilton.tarifas.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.tarifas.dto.ProjTipoHabitacionRequest;
import cl.hilton.tarifas.dto.ProjTipoHabitacionResponse;
import cl.hilton.tarifas.service.ProjTipoHabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tarifas/tipos-habitacion")
@RequiredArgsConstructor
public class ProjTipoHabitacionController {

    private final ProjTipoHabitacionService tipoHabitacionService;

    @GetMapping
    public List<ProjTipoHabitacionResponse> findAll() {
        return tipoHabitacionService.findAll();
    }

    @GetMapping("/codigo/{codigo}")
    public ProjTipoHabitacionResponse findByCodigo(@PathVariable String codigo) {
        return tipoHabitacionService.findByCodigo(codigo);
    }

    @GetMapping("/capacidad/{capacidadMax}")
    public List<ProjTipoHabitacionResponse> findByCapacidadMax(@PathVariable Integer capacidadMax) {
        return tipoHabitacionService.findByCapacidadMax(capacidadMax);
    }

    @GetMapping("/capacidad-minima/{capacidadMax}")
    public List<ProjTipoHabitacionResponse> findByCapacidadMinima(@PathVariable Integer capacidadMax) {
        return tipoHabitacionService.findByCapacidadMinima(capacidadMax);
    }

    @GetMapping("/descripcion/{descripcion}")
    public List<ProjTipoHabitacionResponse> findByDescripcion(@PathVariable String descripcion) {
        return tipoHabitacionService.findByDescripcion(descripcion);
    }

    @GetMapping("/actualizado/{actualizadoEn}")
    public List<ProjTipoHabitacionResponse> findByActualizadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate actualizadoEn) {
        return tipoHabitacionService.findByActualizadoEn(actualizadoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjTipoHabitacionResponse create(@Valid @RequestBody ProjTipoHabitacionRequest request) {
        return tipoHabitacionService.create(request);
    }

    @PostMapping("/sincronizar/codigo/{codigo}")
    public ProjTipoHabitacionResponse sincronizarPorCodigo(@PathVariable String codigo) {
        return tipoHabitacionService.sincronizarPorCodigo(codigo);
    }

    @PutMapping("/codigo/{codigo}")
    public ProjTipoHabitacionResponse update(
            @PathVariable String codigo,
            @Valid @RequestBody ProjTipoHabitacionRequest request) {
        return tipoHabitacionService.update(codigo, request);
    }

    @DeleteMapping("/codigo/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCodigo(@PathVariable String codigo) {
        tipoHabitacionService.deleteByCodigo(codigo);
    }
}
