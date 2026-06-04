package cl.hilton.common.event;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteHabitacionUpdatedEvent implements ReporteHabitacionEvent {

    private Long id;
    private Long asignacionId;
    private String numeroHabitacion;
    private String codigoTarea;
    private Boolean aprobado;
    private String observaciones;
    private String inspector;
    private LocalDate inspeccionadoEn;
}
