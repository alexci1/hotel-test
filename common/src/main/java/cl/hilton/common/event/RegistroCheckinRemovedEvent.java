package cl.hilton.common.event;

import lombok.Data;

@Data
public class RegistroCheckinRemovedEvent implements RegistroCheckinEvent {

    private Long id;
    private String codigoReserva;
}
