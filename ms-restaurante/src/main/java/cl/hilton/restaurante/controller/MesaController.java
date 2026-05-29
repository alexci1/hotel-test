package cl.hilton.restaurante.controller;

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

import cl.hilton.restaurante.dto.MesaRequest;
import cl.hilton.restaurante.dto.MesaResponse;
import cl.hilton.restaurante.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/restaurante/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public List<MesaResponse> findAll() {
        return mesaService.findAll();
    }

    @GetMapping("/{id}")
    public MesaResponse findById(@PathVariable Long id) {
        return mesaService.findById(id);
    }

    @GetMapping("/numero/{numeroMesa}")
    public MesaResponse findByNumeroMesa(@PathVariable String numeroMesa) {
        return mesaService.findByNumeroMesa(numeroMesa);
    }

    @GetMapping("/zona/{zona}")
    public List<MesaResponse> findByZona(@PathVariable String zona) {
        return mesaService.findByZona(zona);
    }

    @GetMapping("/disponible/{disponible}")
    public List<MesaResponse> findByDisponible(@PathVariable Boolean disponible) {
        return mesaService.findByDisponible(disponible);
    }

    @GetMapping("/capacidad-minima/{capacidad}")
    public List<MesaResponse> findByCapacidadMinima(@PathVariable Integer capacidad) {
        return mesaService.findByCapacidadMinima(capacidad);
    }

    @GetMapping("/zona/{zona}/disponible/{disponible}")
    public List<MesaResponse> findByZonaAndDisponible(
            @PathVariable String zona,
            @PathVariable Boolean disponible) {
        return mesaService.findByZonaAndDisponible(zona, disponible);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MesaResponse create(@Valid @RequestBody MesaRequest request) {
        return mesaService.create(request);
    }

    @PutMapping("/{id}")
    public MesaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody MesaRequest request) {
        return mesaService.update(id, request);
    }

    @PatchMapping("/{id}/disponibilidad")
    public MesaResponse cambiarDisponibilidad(
            @PathVariable Long id,
            @RequestParam Boolean disponible) {
        return mesaService.cambiarDisponibilidad(id, disponible);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        mesaService.deleteById(id);
    }
}
