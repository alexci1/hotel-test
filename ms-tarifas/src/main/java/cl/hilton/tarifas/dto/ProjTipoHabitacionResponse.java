package cl.hilton.tarifas.dto;

import lombok.Data;

@Data
public class ProjTipoHabitacionResponse {

    private String codigo;

    private String descripcion;

    private Integer capacidadMax;

    private Boolean activo;
}