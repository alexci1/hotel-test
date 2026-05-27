package cl.hilton.common.event;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RegistroCheckinCreatedEvent implements RegistroCheckinEvent {

    private Long id;
    private String codigoReserva;
    private String emailHuesped;
    private String nombreHuesped;
    private String numeroHabitacion;
    private LocalDate fechaHora;
    private String realizadoPor;
}
