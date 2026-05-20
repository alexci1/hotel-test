package dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiRequest {

    @NotBlank
    @Size(max = 80)
    private String nombre;

    private String descripcion;

    private BigDecimal valorActual;

    private BigDecimal valorObjetivo;

    @Size(max = 30)
    private String unidad;

    @NotBlank
    @Size(max = 20)
    private String periodo;

    private OffsetDateTime actualizadoEn;
}
