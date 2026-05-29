package cl.hilton.housekeeping.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.housekeeping.dto.ReporteRequest;
import cl.hilton.housekeeping.dto.ReporteResponse;
import cl.hilton.housekeeping.model.Asignacion;
import cl.hilton.housekeeping.model.Reporte;

@Mapper(componentModel = "spring")
public interface ReporteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "asignacion", source = "asignacion")
    @Mapping(target = "aprobado", source = "request.aprobado")
    @Mapping(target = "observaciones", source = "request.observaciones")
    @Mapping(target = "inspector", source = "request.inspector")
    @Mapping(target = "inspeccionadoEn", ignore = true)
    Reporte toEntity(ReporteRequest request, Asignacion asignacion);

    @Mapping(target = "asignacionId", source = "asignacion.id")
    @Mapping(target = "numeroHabitacion", source = "asignacion.habitacion.numeroHabitacion")
    @Mapping(target = "codigoTarea", source = "asignacion.tarea.codigo")
    ReporteResponse toResponse(Reporte reporte);

    List<ReporteResponse> toResponseList(List<Reporte> reportes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "asignacion", source = "asignacion")
    @Mapping(target = "aprobado", source = "request.aprobado")
    @Mapping(target = "observaciones", source = "request.observaciones")
    @Mapping(target = "inspector", source = "request.inspector")
    @Mapping(target = "inspeccionadoEn", ignore = true)
    void updateEntity(ReporteRequest request, Asignacion asignacion, @MappingTarget Reporte reporte);
}