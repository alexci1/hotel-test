package cl.hilton.restaurante.dto;

import jakarta.validation.constraints.*;
import lombok.*;

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
    private Integer cantidad;

    @NotNull
    @Min(0)
    private Integer precioUnitUsd;

    @Size(max = 255)
    private String observacion;
}