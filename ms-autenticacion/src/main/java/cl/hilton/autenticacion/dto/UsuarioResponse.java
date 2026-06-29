package cl.hilton.autenticacion.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioResponse extends RepresentationModel<UsuarioResponse> {
    private Long id;
    private String email;
    private String nombreCompleto;
    private String rol;
    private Boolean activo;
}