package cl.hilton.reservas.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DisponibilidadResponse {

    private Long id;

    private String numeroHabitacion;

    private LocalDate fecha;

    private Boolean disponible;
}