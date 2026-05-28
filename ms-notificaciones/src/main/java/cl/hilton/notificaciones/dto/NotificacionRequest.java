package cl.hilton.notificaciones.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NotificacionRequest {

    @NotBlank(message = "El codigo de la plantilla es obligatorio")
    @Size(max = 50, message = "El codigo de la plantilla no puede superar los 50 caracteres")
    private String codigoPlantilla;

    @NotBlank(message = "El email del huesped es obligatorio")
    @Email(message = "El email del huesped debe tener formato valido")
    @Size(max = 120, message = "El email del huesped no puede superar los 120 caracteres")
    private String emailHuesped;

    @NotBlank(message = "El evento origen es obligatorio")
    @Size(max = 80, message = "El evento origen no puede superar los 80 caracteres")
    private String eventoOrigen;

    @Size(max = 500, message = "El payload JSON no puede superar los 500 caracteres")
    private String payloadJson;
}
