package cl.hilton.reservas.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DisponibilidadResponse extends RepresentationModel<DisponibilidadResponse> {
    private Long id;
    private String numeroHabitacion;
    private LocalDate fecha;
    private Boolean disponible;
}