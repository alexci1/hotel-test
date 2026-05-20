package cl.hilton.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoRequest {

    @NotBlank
    @Size(max = 30)
    private String codigoProducto;

    @NotBlank
    @Size(max = 20)
    private String tipo;

    @NotNull
    private Integer cantidad;

    @Size(max = 100)
    private String motivo;

    @NotBlank
    @Size(max = 120)
    private String registradoPor;

    private OffsetDateTime registradoEn;
}
