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
public class LlaveResponse extends RepresentationModel<LlaveResponse> {
    private Long id;
    private String numeroHabitacion;
    private String codigoLlave;
    private Boolean activa;
    private String codigoReserva;
    private LocalDate emitidaEn;
}