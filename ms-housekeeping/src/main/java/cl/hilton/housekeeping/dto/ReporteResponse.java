package cl.hilton.housekeeping.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteResponse {

    private Long id;
    private Long asignacionId;
    private String numeroHabitacion;
    private String codigoTarea;
    private Boolean aprobado;
    private String observaciones;
    private String inspector;
    private LocalDate inspeccionadoEn;
}