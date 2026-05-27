package cl.hilton.common.event;

import lombok.Data;

@Data
public class PagoUpdatedEvent implements PagoEvent {

    private Long id;
    private String numeroFactura;
    private Integer montoUsd;
}
