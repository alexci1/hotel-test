package cl.hilton.reservas.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReservaResponse extends RepresentationModel<ReservaResponse> {
    private Long id;
    private String codigoReserva;
    private String emailHuesped;
    private String numeroHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String estado;
    private LocalDate creadoEn;
}