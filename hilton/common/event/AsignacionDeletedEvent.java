package cl.hilton.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionDeletedEvent implements AsignacionEvent {

    private Long id;
    private String numeroHabitacion;
    private String codigoTarea;
}
