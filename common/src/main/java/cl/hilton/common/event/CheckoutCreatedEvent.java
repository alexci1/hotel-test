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
public class CheckoutCreatedEvent implements CheckoutEvent {

    private Long id;
    private String codigoReserva;
    private LocalDate fechaHora;
    private String realizadoPor;
    private String observaciones;
}
