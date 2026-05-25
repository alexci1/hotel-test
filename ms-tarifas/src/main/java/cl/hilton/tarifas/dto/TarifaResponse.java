package cl.hilton.tarifas.dto;

import lombok.Data;

@Data
public class TarifaResponse {

    private Long id;

    private String codigoTemporada;

    private String codigoTipoHabitacion;

    private Integer precioNocheUsd;

    private Boolean incluyeDesayuno;

    private Boolean activa;
}
