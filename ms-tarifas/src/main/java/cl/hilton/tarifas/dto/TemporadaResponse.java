package cl.hilton.tarifas.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TemporadaResponse {

    private Long id;

    private String codigo;

    private String nombre;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Boolean activa;
}