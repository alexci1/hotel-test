package cl.hilton.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MiniBarRequest {

    @NotBlank
    @Size(max = 10)
    private String numeroHabitacion;

    @NotBlank
    @Size(max = 30)
    private String codigoProducto;

    @NotNull
    @Min(0)
    private Integer cantidad;

    @NotNull
    @Min(0)
    private Integer precioUnitUsd;
}