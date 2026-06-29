package cl.hilton.restaurante.dto;

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
public class MesaResponse extends RepresentationModel<MesaResponse> {
    private Long id;
    private String numeroMesa;
    private Integer capacidad;
    private String zona;
    private Boolean disponible;
}