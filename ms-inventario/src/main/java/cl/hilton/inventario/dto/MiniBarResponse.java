package cl.hilton.inventario.dto;

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
public class MiniBarResponse extends RepresentationModel<MiniBarResponse> {
    private Long id;
    private String numeroHabitacion;
    private String codigoProducto;
    private String nombreProducto;
    private Integer cantidad;
    private Integer precioUnitUsd;
}