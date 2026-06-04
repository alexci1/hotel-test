package cl.hilton.common.event;

import lombok.Data;

@Data
public class MesaRestauranteCreatedEvent implements MesaRestauranteEvent {

    private Long id;
    private String numeroMesa;
    private Integer capacidad;
    private String zona;
    private Boolean disponible;
}
