package cl.hilton.autenticacion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato válido")
    @Size(max = 120, message = "El email no puede superar los 120 caracteres")
    private String email;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre completo no puede superar los 100 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(
        regexp = "ADMIN|GERENCIA|RECEPCION|HOUSEKEEPING|RESTAURANTE|BODEGA|SOLO LECTURA|INACTIVO",
        message = "El tipo de rol debe ser:ADMIN, GERENCIA, RECEPCION, HOUSEKEEPING, RESTAURANTE, BODEGA, SOLO LECTURA o INACTIVO"
    )
    private String rol;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 255, message = "La contraseña no puede superar los 255 caracteres")
    private String hashPassword;

    private Boolean activo;
}
