package cl.hilton.checkin.controller;

import cl.hilton.checkin.dto.LlaveRequest;
import cl.hilton.checkin.dto.LlaveResponse;
import cl.hilton.checkin.service.LlaveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/llaves")
public class LlaveController {

    private final LlaveService llaveService;

    public LlaveController(LlaveService llaveService) {
        this.llaveService = llaveService;
    }

    @GetMapping
    public List<LlaveResponse> listar() {
        return llaveService.listar();
    }

    @GetMapping("/{id}")
    public LlaveResponse buscarPorId(@PathVariable Long id) {
        return llaveService.buscarPorId(id);
    }

    @GetMapping("/codigo/{codigoLlave}")
    public LlaveResponse buscarPorCodigoLlave(@PathVariable String codigoLlave) {
        return llaveService.buscarPorCodigoLlave(codigoLlave);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<LlaveResponse> buscarPorHabitacion(@PathVariable String numeroHabitacion) {
        return llaveService.buscarPorHabitacion(numeroHabitacion);
    }

    @GetMapping("/activa/{activa}")
    public List<LlaveResponse> buscarPorActiva(@PathVariable Boolean activa) {
        return llaveService.buscarPorActiva(activa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LlaveResponse crear(@Valid @RequestBody LlaveRequest request) {
        return llaveService.crear(request);
    }

    @PutMapping("/{id}")
    public LlaveResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody LlaveRequest request
    ) {
        return llaveService.actualizar(id, request);
    }

    @PatchMapping("/{id}/estado")
    public LlaveResponse cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean activa
    ) {
        return llaveService.cambiarEstado(id, activa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        llaveService.eliminar(id);
    }
}