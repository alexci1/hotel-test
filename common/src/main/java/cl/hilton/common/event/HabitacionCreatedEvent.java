package cl.hilton.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
