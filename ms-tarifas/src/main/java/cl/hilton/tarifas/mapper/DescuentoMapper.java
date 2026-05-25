package cl.hilton.tarifas.mapper;

import cl.hilton.tarifas.dto.DescuentoRequest;
import cl.hilton.tarifas.dto.DescuentoResponse;
import cl.hilton.tarifas.model.Descuento;

public class DescuentoMapper {

    public static Descuento toEntity(DescuentoRequest request) {

        return Descuento.builder()
                .codigoDescuento(request.getCodigoDescuento())
                .descripcion(request.getDescripcion())
                .porcentaje(request.getPorcentaje())
                .aplicaA(request.getAplicaA())
                .validoDesde(request.getValidoDesde())
                .validoHasta(request.getValidoHasta())
                .activo(request.getActivo())
                .build();
    }

    public static DescuentoResponse toResponse(Descuento descuento) {

        DescuentoResponse response = new DescuentoResponse();

        response.setId(descuento.getId());
        response.setCodigoDescuento(descuento.getCodigoDescuento());
        response.setDescripcion(descuento.getDescripcion());
        response.setPorcentaje(descuento.getPorcentaje());
        response.setAplicaA(descuento.getAplicaA());
        response.setValidoDesde(descuento.getValidoDesde());
        response.setValidoHasta(descuento.getValidoHasta());
        response.setActivo(descuento.getActivo());

        return response;
    }
}
