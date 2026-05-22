package cl.hilton.habitaciones.dto;

import lombok.Data;

@Data
public class TipoHabitacionResponse {

    private Long id;

    private String codigo;

    private String descripcion;

    private Integer capacidadMax;

    private Boolean activo;
}