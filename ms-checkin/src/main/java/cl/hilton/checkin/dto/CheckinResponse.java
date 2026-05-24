package cl.hilton.checkin.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckinResponse {

    private Long id;
    private String codigoReserva;
    private String emailHuesped;
    private String nombreHuesped;
    private String numeroHabitacion;
    private LocalDate fechaHora;
    private String realizadoPor;
}