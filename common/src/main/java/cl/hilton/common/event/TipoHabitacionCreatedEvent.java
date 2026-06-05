package cl.hilton.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoHabitacionCreatedEvent implements TipoHabitacionEvent {

    private Long id;
    private String codigo;
    private String descripcion;
    private Integer capacidadMax;
    private Boolean activo;
}
