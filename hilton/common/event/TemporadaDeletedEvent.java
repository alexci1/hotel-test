package cl.hilton.common.event;

import lombok.Data;

@Data
public class TemporadaDeletedEvent implements TemporadaEvent {

    private Long id;
    private String codigo;
}
