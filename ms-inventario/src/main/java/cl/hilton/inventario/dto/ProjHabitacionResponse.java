package cl.hilton.inventario.dto;

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
    private LocalDate actualizadoEn;
}