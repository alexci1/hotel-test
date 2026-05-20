package dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricaRequest {

    @NotBlank
    @Size(max = 50)
    private String codigoReporte;

    @NotNull
    private LocalDate periodo;

    @NotBlank
    @Size(max = 80)
    private String nombreMetrica;

    @NotNull
    private BigDecimal valor;

    @Size(max = 30)
    private String unidad;

    private OffsetDateTime calculadoEn;
}
