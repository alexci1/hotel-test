package cl.hilton.pagos.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProjReservaResponse extends RepresentationModel<ProjReservaResponse> {
    private String codigoReserva;
    private String emailHuesped;
    private String numeroHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private LocalDate actualizadoEn;
}