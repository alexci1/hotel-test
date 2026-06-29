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
public class KpiResponse extends RepresentationModel<KpiResponse> {
    private Long id;
    private String codigoReporte;
    private String nombreReporte;
    private String nombre;
    private String descripcion;
    private Integer valorActual;
    private Integer valorObjetivo;
    private String unidad;
    private String periodo;
    private LocalDate actualizadoEn;
}