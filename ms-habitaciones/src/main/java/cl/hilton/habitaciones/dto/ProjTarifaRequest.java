package cl.hilton.habitaciones.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjTarifaRequest {

    @NotBlank(message = "El tipo de habitación es obligatorio")
    @Size(max = 40, message = "El tipo de habitación no puede superar los 40 caracteres")
    private String tipoHabitacion;

    @NotNull(message = "El precio base es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precioBaseUsd;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activa;
}