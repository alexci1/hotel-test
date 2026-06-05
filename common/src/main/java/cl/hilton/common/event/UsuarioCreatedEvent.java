package cl.hilton.common.event;

import lombok.Data;

@Data
public class UsuarioCreatedEvent implements UsuarioEvent {

    private Long id;
    private String email;
    private String nombreCompleto;
    private String rol;
    private Boolean activo;
}
