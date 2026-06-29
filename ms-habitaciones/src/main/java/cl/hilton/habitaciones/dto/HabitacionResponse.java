package cl.hilton.habitaciones.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HabitacionResponse extends RepresentationModel<HabitacionResponse> {
    private Long id;
    private String numeroHabitacion;
    private Integer piso;
    private String codigoTipo;
    private Boolean activa;
}