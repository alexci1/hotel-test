package cl.hilton.common.event;

import lombok.Data;

@Data
public class DescuentoDeletedEvent implements DescuentoEvent {

    private Long id;
    private String codigoDescuento;
}
