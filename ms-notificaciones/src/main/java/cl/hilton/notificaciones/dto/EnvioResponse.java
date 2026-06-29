package cl.hilton.notificaciones.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EnvioResponse extends RepresentationModel<EnvioResponse> {
    private Long id;
    private Long notificacionId;
    private String estado;
    private Integer intentos;
    private LocalDate enviadoEn;
    private String errorMsg;
}