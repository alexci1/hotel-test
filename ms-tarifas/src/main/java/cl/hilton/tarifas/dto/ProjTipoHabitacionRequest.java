package cl.hilton.tarifas.dto;

import lombok.Data;

@Data
public class ProjTipoHabitacionRequest {

    private String codigo;

    private String descripcion;

    private Short capacidadMax;

    private Boolean activo;
}