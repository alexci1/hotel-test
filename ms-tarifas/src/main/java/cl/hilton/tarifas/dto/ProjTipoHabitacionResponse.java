package cl.hilton.tarifas.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProjTipoHabitacionResponse {

    private String codigo;
    private String descripcion;
    private Integer capacidadMax;
    private LocalDate actualizadoEn;
}
