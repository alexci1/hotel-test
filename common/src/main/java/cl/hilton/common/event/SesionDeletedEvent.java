package cl.hilton.common.event;

import lombok.Data;

@Data
public class SesionDeletedEvent implements SesionEvent {

    private Long id;
    private String usuarioEmail;
}
