package cl.hilton.common.event;

import lombok.Data;

@Data
public class DescuentoCreatedEvent implements DescuentoEvent {

    private Long id;
    private String codigoDescuento;
    private Integer porcentaje;
}
