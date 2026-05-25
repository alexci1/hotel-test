package cl.hilton.reportes.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricaResponse {

    private Long id;
    private String codigoReporte;
    private String nombreReporte;
    private LocalDate periodo;
    private String nombreMetrica;
    private Integer valor;
    private String unidad;
    private LocalDate calculadoEn;
}