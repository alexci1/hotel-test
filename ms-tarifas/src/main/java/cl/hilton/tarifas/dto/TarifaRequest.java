package cl.hilton.tarifas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TarifaRequest {

    @NotBlank(message = "El código de tarifa es obligatorio")
    @Size(max = 40, message = "El código no puede superar los 40 caracteres")
    private String codigo;

    @NotNull(message = "El precio base es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "El precio debe ser mayor a 0")
    private BigDecimal precioBaseUsd;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activa;

    private Boolean incluyeDesayuno;
}