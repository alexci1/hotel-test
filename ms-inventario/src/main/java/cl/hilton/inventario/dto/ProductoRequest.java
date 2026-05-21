package cl.hilton.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequest {

    @NotBlank
    @Size(max = 30)
    private String codigoProducto;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    @Size(max = 40)
    private String categoria;

    @NotNull
    @Min(0)
    private Long stockActual;

    @NotNull
    @Min(0)
    private Long stockMinimo;

    @NotBlank
    @Size(max = 20)
    private String unidad;
}