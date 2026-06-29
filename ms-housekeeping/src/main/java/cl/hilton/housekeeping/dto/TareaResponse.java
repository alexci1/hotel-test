package cl.hilton.housekeeping.dto;

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
public class TareaResponse extends RepresentationModel<TareaResponse> {
    private Long id;
    private String codigo;
    private String descripcion;
    private Integer duracionMin;
    private Boolean activa;
}