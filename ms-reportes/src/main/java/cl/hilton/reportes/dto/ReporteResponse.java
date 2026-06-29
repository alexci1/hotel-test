package cl.hilton.reportes.dto;

import java.util.List;
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
public class ReporteResponse extends RepresentationModel<ReporteResponse> {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String frecuencia;
    private Boolean activo;
    private List<MetricaResponse> metricas;
    private List<KpiResponse> kpis;
}