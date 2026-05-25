package cl.hilton.restaurante.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHuespedResponse {

    private String email;
    private String nombreCompleto;
    private String numeroHabitacion;
    private LocalDate actualizadoEn;
}