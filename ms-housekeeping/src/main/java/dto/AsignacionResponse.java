package cl.hilton.housekeeping.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionResponse {

    private Long id;
    private String numeroHabitacion;
    private String tipoHabitacion;
    private String codigoTarea;
    private String descripcionTarea;
    private String emailCamarero;
    private LocalDate fechaProgramada;
    private String estado;
    private Long prioridad;
    private LocalDate iniciadaEn;
    private LocalDate completadaEn;
}