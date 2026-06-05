package cl.hilton.common.event;

import lombok.Data;

@Data
public class PagoCreatedEvent implements PagoEvent {

    private Long id;
    private String numeroFactura;
    private Integer montoUsd;
    private String metodo;
    private String referencia;
    private String pagadoEn;
}
