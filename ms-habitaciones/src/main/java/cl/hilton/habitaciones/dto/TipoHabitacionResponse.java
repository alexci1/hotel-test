package cl.hilton.habitaciones.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TipoHabitacionResponse extends RepresentationModel<TipoHabitacionResponse> {
    private Long id;
    private String codigo;
    private String descripcion;
    private Integer capacidadMax;
    private Boolean activo;
}