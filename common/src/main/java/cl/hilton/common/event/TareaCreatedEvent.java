package cl.hilton.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaCreatedEvent implements TareaEvent {

    private Long id;
    private String codigo;
    private String descripcion;
    private Long duracionMin;
    private Boolean activa;
}
