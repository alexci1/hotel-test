package cl.hilton.checkin.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutResponse {

    private Long id;
    private String codigoReserva;
    private LocalDate fechaHora;
    private String realizadoPor;
    private String observaciones;
}