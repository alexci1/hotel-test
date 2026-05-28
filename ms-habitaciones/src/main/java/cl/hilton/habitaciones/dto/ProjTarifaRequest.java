package cl.hilton.habitaciones.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjTarifaRequest {

    private String tipoHabitacion;

    private BigDecimal precioBaseUsd;
}