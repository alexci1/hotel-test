package cl.hilton.autenticacion.controller;

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

import cl.hilton.autenticacion.dto.RolRequest;
import cl.hilton.autenticacion.dto.RolResponse;
import cl.hilton.autenticacion.service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/autenticacion/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public List<RolResponse> findAll() {
        return rolService.findAll();
    }

    @GetMapping("/{id}")
    public RolResponse findById(@PathVariable Long id) {
        return rolService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public RolResponse findByCodigo(@PathVariable String codigo) {
        return rolService.findByCodigo(codigo);
    }

    @GetMapping("/activo/{activo}")
    public List<RolResponse> findByActivo(@PathVariable Boolean activo) {
        return rolService.findByActivo(activo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RolResponse create(@Valid @RequestBody RolRequest request) {
        return rolService.create(request);
    }

    @PutMapping("/{id}")
    public RolResponse update(
            @PathVariable Long id,
            @Valid @RequestBody RolRequest request) {
        return rolService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        rolService.deleteById(id);
    }
}
