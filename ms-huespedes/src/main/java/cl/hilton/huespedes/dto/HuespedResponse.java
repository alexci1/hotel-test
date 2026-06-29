package cl.hilton.huespedes.dto;

import java.time.LocalDate;
import java.util.List;
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
public class HuespedResponse extends RepresentationModel<HuespedResponse> {
    private Long id;
    private String email;
    private String nombreCompleto;
    private String telefono;
    private Boolean activo;
    private LocalDate creadoEn;
    private List<DocumentoResponse> documentos;
    private PreferenciaResponse preferencia;
}