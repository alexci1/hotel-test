package cl.hilton.tarifas.controller;

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

import cl.hilton.tarifas.dto.TarifaRequest;
import cl.hilton.tarifas.dto.TarifaResponse;
import cl.hilton.tarifas.service.TarifaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tarifas/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    @GetMapping
    public List<TarifaResponse> findAll() {
        return tarifaService.findAll();
    }

    @GetMapping("/{id}")
    public TarifaResponse findById(@PathVariable Long id) {
        return tarifaService.findById(id);
    }

    @GetMapping("/temporada/{codigoTemporada}/tipo/{tipoHabitacion}")
    public TarifaResponse findByTemporadaAndTipoHabitacion(
            @PathVariable String codigoTemporada,
            @PathVariable String tipoHabitacion) {
        return tarifaService.findByTemporadaAndTipoHabitacion(codigoTemporada, tipoHabitacion);
    }

    @GetMapping("/temporada/{codigoTemporada}")
    public List<TarifaResponse> findByCodigoTemporada(@PathVariable String codigoTemporada) {
        return tarifaService.findByCodigoTemporada(codigoTemporada);
    }

    @GetMapping("/tipo/{tipoHabitacion}")
    public List<TarifaResponse> findByTipoHabitacion(@PathVariable String tipoHabitacion) {
        return tarifaService.findByTipoHabitacion(tipoHabitacion);
    }

    @GetMapping("/activa/{activa}")
    public List<TarifaResponse> findByActiva(@PathVariable Boolean activa) {
        return tarifaService.findByActiva(activa);
    }

    @GetMapping("/desayuno/{incluyeDesayuno}")
    public List<TarifaResponse> findByIncluyeDesayuno(@PathVariable Boolean incluyeDesayuno) {
        return tarifaService.findByIncluyeDesayuno(incluyeDesayuno);
    }

    @GetMapping("/tipo/{tipoHabitacion}/activa/{activa}")
    public List<TarifaResponse> findByTipoHabitacionAndActiva(
            @PathVariable String tipoHabitacion,
            @PathVariable Boolean activa) {
        return tarifaService.findByTipoHabitacionAndActiva(tipoHabitacion, activa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarifaResponse create(@Valid @RequestBody TarifaRequest request) {
        return tarifaService.create(request);
    }

    @PutMapping("/{id}")
    public TarifaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TarifaRequest request) {
        return tarifaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tarifaService.deleteById(id);
    }
}
