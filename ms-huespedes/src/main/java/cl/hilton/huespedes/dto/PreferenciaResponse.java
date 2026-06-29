package cl.hilton.huespedes.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PreferenciaResponse extends RepresentationModel<PreferenciaResponse> {
    private Long id;
    private String emailHuesped;
    private String nombreHuesped;
    private Integer pisoPreferido;
    private String tipoCama;
    private String alergias;
    private String observaciones;
}