package cl.hilton.common.event;

import lombok.Data;

@Data
public class TarifaUpdatedEvent implements TarifaEvent {

    private Long id;
    private String codigoTemporada;
    private String codigoTipoHabitacion;
}
