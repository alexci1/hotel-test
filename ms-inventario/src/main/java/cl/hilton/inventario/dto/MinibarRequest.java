package cl.hilton.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinibarRequest {

    @NotBlank
    @Size(max = 10)
    private String numeroHabitacion;

    @NotBlank
    @Size(max = 30)
    private String codigoProducto;

    @NotNull
    @Min(0)
    private Short cantidad;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal precioUnitUsd;
}
