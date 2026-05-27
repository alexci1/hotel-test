package cl.hilton.common.event;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TemporadaCreatedEvent implements TemporadaEvent {

    private Long id;
    private String codigo;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
