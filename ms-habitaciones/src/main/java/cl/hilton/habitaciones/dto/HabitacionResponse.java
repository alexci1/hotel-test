package cl.hilton.habitaciones.dto;

import lombok.Data;

@Data
public class HabitacionResponse {

    private Long id;

    private String numeroHabitacion;

    private Integer piso;

    private String codigoTipo;

    private Boolean activa;
}