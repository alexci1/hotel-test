package cl.hilton.notificaciones.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EnvioResponse {

    private Long id;
    private Long notificacionId;
    private String estado;
    private Integer intentos;
    private LocalDate enviadoEn;
    private String errorMsg;
}
