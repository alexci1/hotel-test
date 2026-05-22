package cl.hilton.tarifas.mapper;

import cl.hilton.tarifas.dto.TemporadaRequest;
import cl.hilton.tarifas.dto.TemporadaResponse;
import cl.hilton.tarifas.model.Temporada;

public class TemporadaMapper {

    public static Temporada toEntity(TemporadaRequest request) {

        return Temporada.builder()
                .codigo(request.getCodigo())
                .nombre(request.getNombre())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .activa(request.getActiva())
                .build();
    }

    public static TemporadaResponse toResponse(Temporada temporada) {

        TemporadaResponse response = new TemporadaResponse();

        response.setId(temporada.getId());
        response.setCodigo(temporada.getCodigo());
        response.setNombre(temporada.getNombre());
        response.setFechaInicio(temporada.getFechaInicio());
        response.setFechaFin(temporada.getFechaFin());
        response.setActiva(temporada.getActiva());

        return response;
    }
}