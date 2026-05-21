package cl.hilton.inventario.controller;

import cl.triskledu.inventario.dto.request.ProjHabitacionRequest;
import cl.triskledu.inventario.dto.response.ProjHabitacionResponse;
import cl.triskledu.inventario.service.ProjHabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proj-habitaciones")
@RequiredArgsConstructor
public class ProjHabitacionController {
    private final ProjHabitacionService service;

    @GetMapping
    public List<ProjHabitacionResponse> listar() { return service.listar(); }

    @GetMapping("/{numeroHabitacion}")
    public ProjHabitacionResponse buscar(@PathVariable String numeroHabitacion) { return service.buscarPorNumeroHabitacion(numeroHabitacion); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjHabitacionResponse crear(@Valid @RequestBody ProjHabitacionRequest request) { return service.crear(request); }

    @PutMapping("/{numeroHabitacion}")
    public ProjHabitacionResponse actualizar(@PathVariable String numeroHabitacion, @Valid @RequestBody ProjHabitacionRequest request) { return service.actualizar(numeroHabitacion, request); }

    @DeleteMapping("/{numeroHabitacion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String numeroHabitacion) { service.eliminar(numeroHabitacion); }
}

