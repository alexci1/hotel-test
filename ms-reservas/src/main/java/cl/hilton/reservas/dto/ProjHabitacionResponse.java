package cl.hilton.reservas.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProjHabitacionResponse extends RepresentationModel<ProjHabitacionResponse> {
    private String numeroHabitacion;
    private String tipo;
    private Boolean activa;
    private LocalDate actualizadoEn;
}