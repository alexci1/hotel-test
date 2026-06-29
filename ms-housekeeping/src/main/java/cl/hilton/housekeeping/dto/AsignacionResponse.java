package cl.hilton.housekeeping.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AsignacionResponse extends RepresentationModel<AsignacionResponse> {
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