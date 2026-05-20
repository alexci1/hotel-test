package cl.hilton.inventario.dto;


import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHabitacionResponse {

    private String numeroHabitacion;
    private String tipo;
    private OffsetDateTime actualizadoEn;
}