package cl.hilton.autenticacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RolRequest {

    @NotBlank(message = "El codigo es obligatorio")
    @Pattern(
        regexp = "ADMIN|GERENCIA|RECEPCION|HOUSEKEEPING|RESTAURANTE|BODEGA|SOLO LECTURA|INACTIVO",
        message = "El codigo del rol debe ser: ADMIN, GERENCIA, RECEPCION, HOUSEKEEPING, RESTAURANTE, BODEGA, SOLO LECTURA o INACTIVO"
    )
    @Size(max = 30, message = "El codigo no puede superar los 30 caracteres")
    private String codigo;

    @Size(max = 100, message = "La descripcion no puede superar los 100 caracteres")
    private String descripcion;

    private Boolean activo;
}
