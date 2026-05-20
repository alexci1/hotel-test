package cl.hilton.restaurante.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoRequest {

    @NotBlank
    @Size(max = 20)
    private String numeroPedido;

    @NotBlank
    @Size(max = 80)
    private String nombreProducto;

    @NotNull
    @Min(1)
    private Short cantidad;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal precioUnitUsd;

    private String observacion;
}
