package cl.hilton.housekeeping.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHabitacionResponse {

    private String numeroHabitacion;
    private String tipo;
    private Integer piso;
    private LocalDate actualizadoEn;
}