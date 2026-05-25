package cl.hilton.tarifas.mapper;

import cl.hilton.tarifas.dto.TarifaRequest;
import cl.hilton.tarifas.dto.TarifaResponse;
import cl.hilton.tarifas.model.ProjTipoHabitacion;
import cl.hilton.tarifas.model.Tarifa;
import cl.hilton.tarifas.model.Temporada;

public class TarifaMapper {

    public static Tarifa toEntity(
            TarifaRequest request,
            Temporada temporada,
            ProjTipoHabitacion tipoHabitacion
    ) {

        return Tarifa.builder()
                .temporada(temporada)
                .tipoHabitacion(tipoHabitacion)
                .precioNocheUsd(request.getPrecioNocheUsd())
                .incluyeDesayuno(request.getIncluyeDesayuno())
                .activa(request.getActiva())
                .build();
    }

    public static TarifaResponse toResponse(Tarifa tarifa) {

        TarifaResponse response = new TarifaResponse();

        response.setId(tarifa.getId());
        response.setCodigoTemporada(tarifa.getTemporada().getCodigo());
        response.setCodigoTipoHabitacion(tarifa.getTipoHabitacion().getCodigo());
        response.setPrecioNocheUsd(tarifa.getPrecioNocheUsd());
        response.setIncluyeDesayuno(tarifa.getIncluyeDesayuno());
        response.setActiva(tarifa.getActiva());

        return response;
    }
}
