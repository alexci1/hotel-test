package cl.hilton.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionCreatedEvent implements HabitacionEvent {

    private Long id;
    private String numeroHabitacion;
    private Integer piso;
    private String codigoTipo;
    private Boolean activa;
}
