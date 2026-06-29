package cl.hilton.habitaciones.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProjTarifaResponse extends RepresentationModel<ProjTarifaResponse> {
    private String tipoHabitacion;
    private Integer precioBaseUsd;
    private LocalDate actualizadoEn;
}