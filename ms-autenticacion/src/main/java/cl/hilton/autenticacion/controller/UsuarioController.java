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

import cl.hilton.autenticacion.dto.UsuarioRequest;
import cl.hilton.autenticacion.dto.UsuarioResponse;
import cl.hilton.autenticacion.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioResponse> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public UsuarioResponse findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @GetMapping("/email/{email}")
    public UsuarioResponse findByEmail(@PathVariable String email) {
        return usuarioService.findByEmail(email);
    }

    @GetMapping("/rol/{rolCodigo}")
    public List<UsuarioResponse> findByRolCodigo(@PathVariable String rolCodigo) {
        return usuarioService.findByRolCodigo(rolCodigo);
    }

    @GetMapping("/activo/{activo}")
    public List<UsuarioResponse> findByActivo(@PathVariable Boolean activo) {
        return usuarioService.findByActivo(activo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse create(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.create(request);
    }

    @PutMapping("/{id}")
    public UsuarioResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {
        return usuarioService.update(id, request);
    }

    @PutMapping("/{id}/activar")
    public UsuarioResponse activar(@PathVariable Long id) {
        return usuarioService.activar(id);
    }

    @PutMapping("/{id}/desactivar")
    public UsuarioResponse desactivar(@PathVariable Long id) {
        return usuarioService.desactivar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }
}
