package dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricaResponse {

    private Integer id;
    private String codigoReporte;
    private String nombreReporte;
    private LocalDate periodo;
    private String nombreMetrica;
    private BigDecimal valor;
    private String unidad;
    private OffsetDateTime calculadoEn;
}


