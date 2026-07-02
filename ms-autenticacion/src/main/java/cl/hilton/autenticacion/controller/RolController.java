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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @Operation(summary = "Listar roles", description = "Retorna todos los roles registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Roles encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RolResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron roles", content = @Content)
    })
    @GetMapping
    public List<RolResponse> findAll() {
        return rolService.findAll();
    }

    @Operation(summary = "Obtener rol por ID", description = "Retorna un rol según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol encontrado",
            content = @Content(schema = @Schema(implementation = RolResponse.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public RolResponse findById(@PathVariable Long id) {
        return rolService.findById(id);
    }

    @Operation(summary = "Obtener rol por código", description = "Retorna un rol según su código único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol encontrado",
            content = @Content(schema = @Schema(implementation = RolResponse.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @GetMapping("/codigo/{codigo}")
    public RolResponse findByCodigo(@PathVariable String codigo) {
        return rolService.findByCodigo(codigo);
    }

    @Operation(summary = "Listar roles por estado activo", description = "Retorna los roles filtrados por su estado activo o inactivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Roles encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RolResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron roles con el estado indicado", content = @Content)
    })
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
