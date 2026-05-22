package cl.hilton.reservas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CancelacionRequest {

    @NotBlank(message = "El código de reserva es obligatorio")
    private String codigoReserva;

    @Size(max = 200, message = "Máximo 200 caracteres")
    private String motivo;

    @Size(max = 80, message = "Máximo 80 caracteres")
    private String canceladoPor;

    @NotNull(message = "La penalidad es obligatoria")
    private BigDecimal penalidadUsd;
}