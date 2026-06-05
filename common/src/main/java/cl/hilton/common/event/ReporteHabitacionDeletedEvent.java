package cl.hilton.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteHabitacionDeletedEvent implements ReporteHabitacionEvent {

    private Long id;
    private Long asignacionId;
}
