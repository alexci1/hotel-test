package cl.hilton.tarifas.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProjTipoHabitacionResponse extends RepresentationModel<ProjTipoHabitacionResponse> {
    private String codigo;
    private String descripcion;
    private Integer capacidadMax;
    private LocalDate actualizadoEn;
}