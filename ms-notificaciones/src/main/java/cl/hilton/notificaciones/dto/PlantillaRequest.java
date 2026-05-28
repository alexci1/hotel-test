package cl.hilton.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlantillaRequest {

    @NotBlank(message = "El codigo es obligatorio")
    @Size(max = 50, message = "El codigo no puede superar los 50 caracteres")
    private String codigo;

    @NotBlank(message = "El canal es obligatorio")
    @Pattern(
        regexp = "EMAIL|SMS|PUSH|WHATSAPP",
        message = "El canal debe ser: EMAIL, SMS, PUSH o WHATSAPP"
    )
    private String canal;

    @Size(max = 200, message = "El asunto no puede superar los 200 caracteres")
    private String asunto;

    @NotBlank(message = "El cuerpo es obligatorio")
    @Size(max = 1000, message = "El cuerpo no puede superar los 1000 caracteres")
    private String cuerpo;

    private Boolean activa;
}
