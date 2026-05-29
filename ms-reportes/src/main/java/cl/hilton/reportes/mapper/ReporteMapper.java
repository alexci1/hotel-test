package cl.hilton.reportes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.reportes.dto.ReporteRequest;
import cl.hilton.reportes.dto.ReporteResponse;
import cl.hilton.reportes.model.Reporte;

@Mapper(componentModel = "spring")
public interface ReporteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "metricas", ignore = true)
    @Mapping(target = "kpis", ignore = true)
    Reporte toEntity(ReporteRequest request);

    ReporteResponse toResponse(Reporte reporte);

    List<ReporteResponse> toResponseList(List<Reporte> reportes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "metricas", ignore = true)
    @Mapping(target = "kpis", ignore = true)
    void updateEntity(ReporteRequest request, @MappingTarget Reporte reporte);
}