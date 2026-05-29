package cl.hilton.reportes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.reportes.dto.KpiRequest;
import cl.hilton.reportes.dto.KpiResponse;
import cl.hilton.reportes.model.Kpi;

@Mapper(componentModel = "spring")
public interface KpiMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    Kpi toEntity(KpiRequest request);

    KpiResponse toResponse(Kpi kpi);

    List<KpiResponse> toResponseList(List<Kpi> kpis);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    void updateEntity(KpiRequest request, @MappingTarget Kpi kpi);
}
