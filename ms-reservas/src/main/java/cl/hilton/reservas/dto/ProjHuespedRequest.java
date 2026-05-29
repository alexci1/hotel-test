package cl.hilton.reservas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProjHuespedRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ingresar un email válido")
    @Size(max = 120, message = "El email no puede superar los 120 caracteres")
    private String email;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
    private String nombreCompleto;

    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    private String telefono;
}