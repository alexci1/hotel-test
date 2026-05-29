package cl.hilton.restaurante.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ItemPedidoRequest {

    @NotBlank(message = "El numero de pedido es obligatorio")
    @Size(max = 20, message = "El numero de pedido no puede superar los 20 caracteres")
    private String numeroPedido;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 80, message = "El nombre del producto no puede superar los 80 caracteres")
    private String nombreProducto;

    @Min(value = 1, message = "La cantidad debe ser mayor que cero")
    private Integer cantidad;

    @NotNull(message = "El precio unitario en USD es obligatorio")
    @Min(value = 0, message = "El precio unitario no puede ser negativo")
    private Integer precioUnitUsd;

    @Size(max = 255, message = "La observacion no puede superar los 255 caracteres")
    private String observacion;
}
