package cl.hilton.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MiniBarRequest {

    @NotBlank(message = "El numero de habitacion es obligatorio")
    @Size(max = 10, message = "El numero de habitacion no puede superar los 10 caracteres")
    private String numeroHabitacion;

    @NotBlank(message = "El codigo del producto es obligatorio")
    @Size(max = 30, message = "El codigo del producto no puede superar los 30 caracteres")
    private String codigoProducto;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio unitario no puede ser negativo")
    private Integer precioUnitUsd;
}
