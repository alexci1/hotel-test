package cl.hilton.habitaciones.controller;

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

import cl.hilton.habitaciones.dto.ProjTarifaRequest;
import cl.hilton.habitaciones.dto.ProjTarifaResponse;
import cl.hilton.habitaciones.service.ProjTarifaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tarifas")
@RequiredArgsConstructor
public class ProjTarifaController {

    private final ProjTarifaService projTarifaService;

    @GetMapping
    public List<ProjTarifaResponse> findAll() {
        return projTarifaService.findAll();
    }

    @GetMapping("/tipo/{tipoHabitacion}")
    public ProjTarifaResponse findByTipoHabitacion(@PathVariable String tipoHabitacion) {
        return projTarifaService.findByTipoHabitacion(tipoHabitacion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjTarifaResponse create(@Valid @RequestBody ProjTarifaRequest request) {
        return projTarifaService.create(request);
    }

    @PostMapping("/sincronizar/tipo/{tipoHabitacion}")
    public ProjTarifaResponse sincronizarPorTipoHabitacion(@PathVariable String tipoHabitacion) {
        return projTarifaService.sincronizarPorTipoHabitacion(tipoHabitacion);
    }

    @PutMapping("/tipo/{tipoHabitacion}")
    public ProjTarifaResponse update(
            @PathVariable String tipoHabitacion,
            @Valid @RequestBody ProjTarifaRequest request) {
        return projTarifaService.update(tipoHabitacion, request);
    }

    @DeleteMapping("/tipo/{tipoHabitacion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByTipoHabitacion(@PathVariable String tipoHabitacion) {
        projTarifaService.deleteByTipoHabitacion(tipoHabitacion);
    }
}
