package cl.hilton.housekeeping.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.housekeeping.dto.AsignacionRequest;
import cl.hilton.housekeeping.dto.AsignacionResponse;
import cl.hilton.housekeeping.model.Asignacion;
import cl.hilton.housekeeping.model.ProjHabitacion;
import cl.hilton.housekeeping.model.Tarea;

@Mapper(componentModel = "spring")
public interface AsignacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitacion", source = "habitacion")
    @Mapping(target = "tarea", source = "tarea")
    @Mapping(target = "emailCamarero", source = "request.emailCamarero")
    @Mapping(target = "fechaProgramada", source = "request.fechaProgramada")
    @Mapping(target = "estado", source = "request.estado")
    @Mapping(target = "prioridad", source = "request.prioridad")
    @Mapping(target = "iniciadaEn", source = "request.iniciadaEn")
    @Mapping(target = "completadaEn", source = "request.completadaEn")
    @Mapping(target = "reporte", ignore = true)
    Asignacion toEntity(AsignacionRequest request, ProjHabitacion habitacion, Tarea tarea);

    @Mapping(target = "numeroHabitacion", source = "habitacion.numeroHabitacion")
    @Mapping(target = "tipoHabitacion", source = "habitacion.tipo")
    @Mapping(target = "codigoTarea", source = "tarea.codigo")
    @Mapping(target = "descripcionTarea", source = "tarea.descripcion")
    AsignacionResponse toResponse(Asignacion asignacion);

    List<AsignacionResponse> toResponseList(List<Asignacion> asignaciones);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitacion", source = "habitacion")
    @Mapping(target = "tarea", source = "tarea")
    @Mapping(target = "emailCamarero", source = "request.emailCamarero")
    @Mapping(target = "fechaProgramada", source = "request.fechaProgramada")
    @Mapping(target = "estado", source = "request.estado")
    @Mapping(target = "prioridad", source = "request.prioridad")
    @Mapping(target = "iniciadaEn", source = "request.iniciadaEn")
    @Mapping(target = "completadaEn", source = "request.completadaEn")
    @Mapping(target = "reporte", ignore = true)
    void updateEntity(AsignacionRequest request, ProjHabitacion habitacion, Tarea tarea, @MappingTarget Asignacion asignacion);
}