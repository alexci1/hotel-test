package cl.hilton.common.event;

import lombok.Data;

@Data
public class TarifaDeletedEvent implements TarifaEvent {

    private Long id;
    private String codigoTemporada;
    private String codigoTipoHabitacion;
}
