package cl.hilton.autenticacion.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RolResponse extends RepresentationModel<RolResponse> {
    private Long id;
    private String codigo;
    private String descripcion;
    private Boolean activo;
}