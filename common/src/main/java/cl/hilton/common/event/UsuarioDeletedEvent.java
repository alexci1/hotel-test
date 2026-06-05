package cl.hilton.common.event;

import lombok.Data;

@Data
public class UsuarioDeletedEvent implements UsuarioEvent {

    private Long id;
    private String email;
}
