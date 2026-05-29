package cl.hilton.habitaciones.dto;

import lombok.Data;

@Data
public class TarifaHabitacionResponse {

    private Long id;
    private String codigoTemporada;
    private String codigoTipoHabitacion;
    private Integer precioNocheUsd;
    private Boolean incluyeDesayuno;
    private Boolean activa;
}
