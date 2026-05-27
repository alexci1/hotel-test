package cl.hilton.common.event;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionUpdatedEvent implements AsignacionEvent {

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
