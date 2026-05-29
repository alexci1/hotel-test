package cl.hilton.habitaciones.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProjTarifaResponse {

    private String tipoHabitacion;
    private Integer precioBaseUsd;
    private LocalDate actualizadoEn;
}
