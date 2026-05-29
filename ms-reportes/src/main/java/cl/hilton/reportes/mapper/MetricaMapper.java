package cl.hilton.reportes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.reportes.dto.MetricaRequest;
import cl.hilton.reportes.dto.MetricaResponse;
import cl.hilton.reportes.model.Metrica;

@Mapper(componentModel = "spring")
public interface MetricaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reporte", ignore = true)
    @Mapping(target = "calculadoEn", ignore = true)
    Metrica toEntity(MetricaRequest request);

    @Mapping(target = "codigoReporte", source = "reporte.codigo")
    @Mapping(target = "nombreReporte", source = "reporte.nombre")
    MetricaResponse toResponse(Metrica metrica);

    List<MetricaResponse> toResponseList(List<Metrica> metricas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reporte", ignore = true)
    @Mapping(target = "calculadoEn", ignore = true)
    void updateEntity(MetricaRequest request, @MappingTarget Metrica metrica);
}
