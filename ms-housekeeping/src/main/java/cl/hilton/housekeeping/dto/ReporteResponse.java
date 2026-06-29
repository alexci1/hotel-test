package cl.hilton.housekeeping.dto;

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
public class ReporteResponse extends RepresentationModel<ReporteResponse> {
    private Long id;
    private Long asignacionId;
    private String numeroHabitacion;
    private String codigoTarea;
    private Boolean aprobado;
    private String observaciones;
    private String inspector;
    private LocalDate inspeccionadoEn;
}