package cl.hilton.checkin.dto;

import java.time.LocalDate;
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
public class ProjHuespedResponse extends RepresentationModel<ProjHuespedResponse> {
    private String email;
    private String nombreCompleto;
    private LocalDate actualizadoEn;
}