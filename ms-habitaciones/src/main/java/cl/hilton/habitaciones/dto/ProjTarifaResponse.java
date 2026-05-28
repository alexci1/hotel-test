package cl.hilton.habitaciones.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjTarifaResponse {

    private String tipoHabitacion;

    private BigDecimal precioBaseUsd;

    private Boolean activa;
}