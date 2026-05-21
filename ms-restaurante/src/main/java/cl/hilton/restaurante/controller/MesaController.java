package cl.hilton.restaurante.controller;


import java.util.List;
import cl.hilton.restaurante.dto.MesaRequest;
import cl.hilton.restaurante.dto.MesaResponse;
import cl.hilton.restaurante.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public List<MesaResponse> listar() {
        return mesaService.listar();
    }

    @GetMapping("/{id}")
    public MesaResponse buscarPorId(@PathVariable Integer id) {
        return mesaService.buscarPorId(id);
    }

    @GetMapping("/numero/{numeroMesa}")
    public MesaResponse buscarPorNumeroMesa(@PathVariable String numeroMesa) {
        return mesaService.buscarPorNumeroMesa(numeroMesa);
    }

    @GetMapping("/zona/{zona}")
    public List<MesaResponse> buscarPorZona(@PathVariable String zona) {
        return mesaService.buscarPorZona(zona);
    }

    @GetMapping("/disponibilidad")
    public List<MesaResponse> buscarPorDisponibilidad(@RequestParam Boolean disponible) {
        return mesaService.buscarPorDisponibilidad(disponible);
    }

    @GetMapping("/capacidad")
    public List<MesaResponse> buscarPorCapacidadMinima(@RequestParam Short capacidad) {
        return mesaService.buscarPorCapacidadMinima(capacidad);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MesaResponse crear(@Valid @RequestBody MesaRequest request) {
        return mesaService.crear(request);
    }

    @PutMapping("/{id}")
    public MesaResponse actualizar(@PathVariable Integer id, @Valid @RequestBody MesaRequest request) {
        return mesaService.actualizar(id, request);
    }

    @PatchMapping("/{id}/disponibilidad")
    public MesaResponse cambiarDisponibilidad(@PathVariable Integer id, @RequestParam Boolean disponible) {
        return mesaService.cambiarDisponibilidad(id, disponible);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        mesaService.eliminar(id);
    }
}

