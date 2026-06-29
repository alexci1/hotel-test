package cl.hilton.tarifas.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TarifaResponse extends RepresentationModel<TarifaResponse> {
    private Long id;
    private String codigoTemporada;
    private String codigoTipoHabitacion;
    private Integer precioNocheUsd;
    private Boolean incluyeDesayuno;
    private Boolean activa;
}