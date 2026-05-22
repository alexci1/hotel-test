package cl.hilton.tarifas.mapper;

import cl.hilton.tarifas.dto.TarifaRequest;
import cl.hilton.tarifas.dto.TarifaResponse;
import cl.hilton.tarifas.model.Tarifa;

public class TarifaMapper {

    public static Tarifa toEntity(TarifaRequest request) {

        return Tarifa.builder()
                .codigo(request.getCodigo())
                .precioBaseUsd(request.getPrecioBaseUsd())
                .activa(request.getActiva())
                .incluyeDesayuno(request.getIncluyeDesayuno())
                .build();
    }

    public static TarifaResponse toResponse(Tarifa tarifa) {

        TarifaResponse response = new TarifaResponse();

        response.setId(tarifa.getId());
        response.setCodigo(tarifa.getCodigo());
        response.setPrecioBaseUsd(tarifa.getPrecioBaseUsd());
        response.setActiva(tarifa.getActiva());
        response.setIncluyeDesayuno(tarifa.getIncluyeDesayuno());

        return response;
    }
}