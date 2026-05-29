package cl.hilton.reservas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CancelacionRequest {

    @NotBlank(message = "El codigo de reserva es obligatorio")
    @Size(max = 20, message = "El codigo de reserva no puede superar los 20 caracteres")
    private String codigoReserva;

    @Size(max = 200, message = "El motivo no puede superar los 200 caracteres")
    private String motivo;

    @Size(max = 80, message = "El usuario que cancela no puede superar los 80 caracteres")
    private String canceladoPor;

    @Min(value = 0, message = "La penalidad no puede ser negativa")
    private Integer penalidadUsd;
}
