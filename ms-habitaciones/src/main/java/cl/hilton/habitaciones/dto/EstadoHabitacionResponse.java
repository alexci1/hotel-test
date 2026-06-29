package cl.hilton.habitaciones.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EstadoHabitacionResponse extends RepresentationModel<EstadoHabitacionResponse> {
    private Long id;
    private String numeroHabitacion;
    private String estado;
    private String observacion;
    private LocalDate actualizadoEn;
}