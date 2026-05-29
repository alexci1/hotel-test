package cl.hilton.inventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MovimientoRequest {

    @NotBlank
    @Size(max = 30)
    private String codigoProducto;

    @NotBlank
    @Pattern(regexp = "ENTRADA|SALIDA|AJUSTE|DEVOLUCION")
    private String tipo;

    @NotNull
    private Integer cantidad;

    @Size(max = 100)
    private String motivo;

    @NotBlank
    @Size(max = 120)
    private String registradoPor;
}
