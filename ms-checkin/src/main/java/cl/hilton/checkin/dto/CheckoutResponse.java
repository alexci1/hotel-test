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
public class CheckoutResponse extends RepresentationModel<CheckoutResponse> {
    private Long id;
    private String codigoReserva;
    private LocalDate fechaHora;
    private String realizadoPor;
    private String observaciones;
}