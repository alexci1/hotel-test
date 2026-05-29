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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.habitaciones.dto.TipoHabitacionRequest;
import cl.hilton.habitaciones.dto.TipoHabitacionResponse;
import cl.hilton.habitaciones.service.TipoHabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/habitaciones/tipos-habitacion")
@RequiredArgsConstructor
public class TipoHabitacionController {

    private final TipoHabitacionService tipoHabitacionService;

    @GetMapping
    public List<TipoHabitacionResponse> findAll() {
        return tipoHabitacionService.findAll();
    }

    @GetMapping("/{id}")
    public TipoHabitacionResponse findById(@PathVariable Long id) {
        return tipoHabitacionService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public TipoHabitacionResponse findByCodigo(@PathVariable String codigo) {
        return tipoHabitacionService.findByCodigo(codigo);
    }

    @GetMapping("/activos/{activo}")
    public List<TipoHabitacionResponse> findByActivo(@PathVariable Boolean activo) {
        return tipoHabitacionService.findByActivo(activo);
    }

    @GetMapping("/capacidad/{capacidadMax}")
    public List<TipoHabitacionResponse> findByCapacidadMax(@PathVariable Integer capacidadMax) {
        return tipoHabitacionService.findByCapacidadMax(capacidadMax);
    }

    @GetMapping("/capacidad-minima/{capacidadMax}")
    public List<TipoHabitacionResponse> findByCapacidadMinima(@PathVariable Integer capacidadMax) {
        return tipoHabitacionService.findByCapacidadMinima(capacidadMax);
    }

    @GetMapping("/buscar")
    public List<TipoHabitacionResponse> findByDescripcion(@RequestParam String descripcion) {
        return tipoHabitacionService.findByDescripcion(descripcion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoHabitacionResponse create(@Valid @RequestBody TipoHabitacionRequest request) {
        return tipoHabitacionService.create(request);
    }

    @PutMapping("/{id}")
    public TipoHabitacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TipoHabitacionRequest request) {
        return tipoHabitacionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tipoHabitacionService.deleteById(id);
    }
}
