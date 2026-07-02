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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Listar usuarios", description = "Retorna todos los usuarios registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron usuarios", content = @Content)
    })
    @GetMapping
    public List<UsuarioResponse> findAll() {
        return usuarioService.findAll();
    }

    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public UsuarioResponse findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @Operation(summary = "Obtener usuario por email", description = "Retorna un usuario según su correo electrónico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/email/{email}")
    public UsuarioResponse findByEmail(@PathVariable String email) {
        return usuarioService.findByEmail(email);
    }

    @Operation(summary = "Listar usuarios por rol", description = "Retorna los usuarios asociados a un código de rol")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron usuarios para el rol indicado", content = @Content)
    })
    @GetMapping("/rol/{rolCodigo}")
    public List<UsuarioResponse> findByRolCodigo(@PathVariable String rolCodigo) {
        return usuarioService.findByRolCodigo(rolCodigo);
    }

    @Operation(summary = "Listar usuarios por estado activo", description = "Retorna los usuarios filtrados por su estado activo o inactivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron usuarios con el estado indicado", content = @Content)
    })
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
