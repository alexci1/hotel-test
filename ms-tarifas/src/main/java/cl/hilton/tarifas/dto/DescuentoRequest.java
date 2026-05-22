package cl.hilton.tarifas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DescuentoRequest {

    @NotBlank(message = "El código de descuento es obligatorio")
    @Size(max = 40, message = "El código no puede superar los 40 caracteres")
    private String codigoDescuento;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no puede superar los 100 caracteres")
    private String descripcion;

    @NotNull(message = "El porcentaje es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "El porcentaje debe ser mayor a 0")
    private BigDecimal porcentaje;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}