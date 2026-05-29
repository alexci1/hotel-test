package cl.hilton.reservas.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProjHabitacionResponse {

    private String numeroHabitacion;
    private String tipo;
    private Boolean activa;
    private LocalDate actualizadoEn;
}
