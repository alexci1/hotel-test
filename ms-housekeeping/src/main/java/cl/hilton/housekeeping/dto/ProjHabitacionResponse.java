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
public class ProjHabitacionResponse extends RepresentationModel<ProjHabitacionResponse> {
    private String numeroHabitacion;
    private String tipo;
    private Integer piso;
    private LocalDate actualizadoEn;
}