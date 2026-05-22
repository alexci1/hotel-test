package cl.hilton.habitaciones.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EstadoHabitacionResponse {

    private Long id;

    private String numeroHabitacion;

    private String estado;

    private String observacion;

    private LocalDate actualizadoEn;
}