package cl.hilton.notificaciones.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotificacionResponse extends RepresentationModel<NotificacionResponse> {
    private Long id;
    private String codigoPlantilla;
    private String emailHuesped;
    private String eventoOrigen;
    private String payloadJson;
    private LocalDate creadoEn;
}