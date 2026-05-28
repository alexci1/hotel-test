package cl.hilton.notificaciones.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProjHuespedResponse {

    private String email;
    private String nombreCompleto;
    private LocalDate actualizadoEn;
}
