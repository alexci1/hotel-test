package cl.hilton.common.event;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SesionCreatedEvent implements SesionEvent {

    private Long id;
    private String usuarioEmail;
    private String ipOrigen;
    private String userAgent;
    private LocalDate expiraEn;
    private LocalDate creadaEn;
    private Boolean invalidada;
}
