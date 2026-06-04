package cl.hilton.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionUpdatedEvent implements NotificacionEvent {

    private Long id;
    private String codigoPlantilla;
    private String emailHuesped;
    private String eventoOrigen;
    private String payloadJson;
    private String creadoEn;
}
