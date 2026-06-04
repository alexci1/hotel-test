package cl.hilton.common.event;

import lombok.Data;

@Data
public class PagoDeletedEvent implements PagoEvent {

    private Long id;
    private String numeroFactura;
}
