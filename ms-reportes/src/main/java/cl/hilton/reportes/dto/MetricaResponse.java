package cl.hilton.reportes.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MetricaResponse extends RepresentationModel<MetricaResponse> {
    private Long id;
    private String codigoReporte;
    private String nombreReporte;
    private LocalDate periodo;
    private String nombreMetrica;
    private Integer valor;
    private String unidad;
    private LocalDate calculadoEn;
}