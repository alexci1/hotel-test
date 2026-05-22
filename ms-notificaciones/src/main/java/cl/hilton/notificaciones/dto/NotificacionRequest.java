package cl.hilton.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificacionRequest {

    @NotBlank(message = "El codigo de la plantilla es obligatorio")
    private String codigoPlantilla;

    @NotBlank(message = "El email del huesped es obligatorio")
    private String emailHuesped;

    @NotBlank(message = "El evento origen es obligatorio")
    private String eventoOrigen;

    private String payloadJson;
}