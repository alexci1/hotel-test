package cl.hilton.reservas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CancelacionRequest {

    @NotBlank(message = "El código de reserva es obligatorio")
    private String codigoReserva;

    @Size(max = 200, message = "Máximo 200 caracteres")
    private String motivo;

    @Size(max = 80, message = "Máximo 80 caracteres")
    private String canceladoPor;

    @NotNull(message = "La penalidad es obligatoria")
    @Min(value = 0, message = "La penalidad no puede ser negativa")
    private Integer penalidadUsd;
}
