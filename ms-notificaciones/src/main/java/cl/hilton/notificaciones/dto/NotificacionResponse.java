package cl.hilton.notificaciones.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NotificacionResponse {

    private Long id;
    private String codigoPlantilla;
    private String emailHuesped;
    private String eventoOrigen;
    private String payloadJson;
    private LocalDate creadoEn;
}
