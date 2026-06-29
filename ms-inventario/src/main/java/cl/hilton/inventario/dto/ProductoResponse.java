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
public class ProductoResponse extends RepresentationModel<ProductoResponse> {
    private Long id;
    private String codigoProducto;
    private String nombre;
    private String categoria;
    private Integer stockActual;
    private Integer stockMinimo;
    private String unidad;
}