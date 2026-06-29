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
public class CheckinResponse extends RepresentationModel<CheckinResponse> {
    private Long id;
    private String codigoReserva;
    private String emailHuesped;
    private String nombreHuesped;
    private String numeroHabitacion;
    private LocalDate fechaHora;
    private String realizadoPor;
}