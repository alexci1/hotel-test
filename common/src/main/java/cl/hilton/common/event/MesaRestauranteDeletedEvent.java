package cl.hilton.common.event;

import lombok.Data;

@Data
public class MesaRestauranteDeletedEvent implements MesaRestauranteEvent {

    private Long id;
    private String numeroMesa;
}
