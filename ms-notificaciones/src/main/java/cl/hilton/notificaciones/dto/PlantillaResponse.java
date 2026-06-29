package cl.hilton.notificaciones.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlantillaResponse extends RepresentationModel<PlantillaResponse> {
    private Long id;
    private String codigo;
    private String canal;
    private String asunto;
    private String cuerpo;
    private Boolean activa;
}