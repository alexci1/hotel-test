package cl.hilton.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoRequest {

    @NotBlank(message = "El codigo del producto es obligatorio")
    @Size(max = 30, message = "El codigo del producto no puede superar los 30 caracteres")
    private String codigoProducto;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La categoria es obligatoria")
    @Pattern(
        regexp = "AMENITY|MINIBAR|LIMPIEZA|LENCERIA|MANTENIMIENTO|OTRO",
        message = "La categoria debe ser: AMENITY, MINIBAR, LIMPIEZA, LENCERIA, MANTENIMIENTO u OTRO"
    )
    private String categoria;

    @Min(value = 0, message = "El stock actual no puede ser negativo")
    private Integer stockActual;

    @Min(value = 0, message = "El stock minimo no puede ser negativo")
    private Integer stockMinimo;

    @Size(max = 20, message = "La unidad no puede superar los 20 caracteres")
    private String unidad;
}
