package cl.hilton.common.event;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelacionUpdatedEvent implements CancelacionEvent {

    private Long id;
    private String codigoReserva;
    private String motivo;
    private String canceladoPor;
    private LocalDate canceladoEn;
    private Integer penalidadUsd;
}
