package cl.hilton.notificaciones.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnvioRequest {

    @NotNull(message = "El id de la notificacion es obligatorio")
    private Long notificacionId;

    @Pattern(
        regexp = "PENDIENTE|ENVIADO|FALLIDO|RECHAZADO",
        message = "El estado debe ser: PENDIENTE, ENVIADO, FALLIDO o RECHAZADO"
    )
    private String estado;

    @PositiveOrZero(message = "Los intentos no pueden ser negativos")
    private Integer intentos;

    @Size(max = 255, message = "El mensaje de error no puede superar los 255 caracteres")
    private String errorMsg;
}
