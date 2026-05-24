package cl.hilton.checkin.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LlaveResponse {

    private Long id;
    private String numeroHabitacion;
    private String codigoLlave;
    private Boolean activa;
    private String codigoReserva;
    private LocalDate emitidaEn;
}