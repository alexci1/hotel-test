package cl.hilton.autenticacion.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SesionRequest {

    @NotBlank(message = "El email del usuario es obligatorio")
    @Email(message = "El email del usuario debe tener formato válido")
    @Size(max = 120, message = "El email del usuario no puede superar los 120 caracteres")
    private String usuarioEmail;

    @NotBlank(message = "El token es obligatorio")
    @Size(max = 255, message = "El token no puede superar los 255 caracteres")
    private String tokenHash;

    @NotBlank(message = "La ip de origen es obligatoria")
    @Size(max = 45, message = "La ip de origen no puede superar los 45 caracteres")
    private String ipOrigen;

    @Size(max = 250, message = "El user agent no puede superar los 250 caracteres")
    private String userAgent;

    @NotNull(message = "La fecha de expiracion es obligatoria")
    private LocalDate expiraEn;

    @NotNull(message = "La fecha de creacion es obligatoria")
    private LocalDate creadaEn;

    private Boolean invalidada;
}
