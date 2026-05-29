package cl.hilton.reservas.dto;

import lombok.Data;

@Data
public class ProjHabitacionResponse {

    private String numeroHabitacion;

    private String tipoHabitacion;

    private Integer capacidadMax;

    private Boolean activa;
}