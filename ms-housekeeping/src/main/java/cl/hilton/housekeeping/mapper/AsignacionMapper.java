package cl.hilton.housekeeping.mapper;

import cl.hilton.housekeeping.dto.AsignacionRequest;
import cl.hilton.housekeeping.dto.AsignacionResponse;
import cl.hilton.housekeeping.model.Asignacion;
import cl.hilton.housekeeping.model.ProjHabitacion;
import cl.hilton.housekeeping.model.Tarea;
import org.springframework.stereotype.Component;

@Component
public class AsignacionMapper {

    public Asignacion toEntity(AsignacionRequest request, ProjHabitacion habitacion, Tarea tarea) {
        return Asignacion.builder()
                .habitacion(habitacion)
                .tarea(tarea)
                .emailCamarero(request.getEmailCamarero())
                .fechaProgramada(request.getFechaProgramada())
                .estado(request.getEstado())
                .prioridad(request.getPrioridad())
                .iniciadaEn(request.getIniciadaEn())
                .completadaEn(request.getCompletadaEn())
                .build();
    }

    public AsignacionResponse toResponse(Asignacion asignacion) {
        return AsignacionResponse.builder()
                .id(asignacion.getId())
                .numeroHabitacion(asignacion.getHabitacion().getNumeroHabitacion())
                .tipoHabitacion(asignacion.getHabitacion().getTipo())
                .codigoTarea(asignacion.getTarea().getCodigo())
                .descripcionTarea(asignacion.getTarea().getDescripcion())
                .emailCamarero(asignacion.getEmailCamarero())
                .fechaProgramada(asignacion.getFechaProgramada())
                .estado(asignacion.getEstado())
                .prioridad(asignacion.getPrioridad())
                .iniciadaEn(asignacion.getIniciadaEn())
                .completadaEn(asignacion.getCompletadaEn())
                .build();
    }

    public void updateEntity(Asignacion asignacion, AsignacionRequest request, ProjHabitacion habitacion, Tarea tarea) {
        asignacion.setHabitacion(habitacion);
        asignacion.setTarea(tarea);
        asignacion.setEmailCamarero(request.getEmailCamarero());
        asignacion.setFechaProgramada(request.getFechaProgramada());
        asignacion.setEstado(request.getEstado());
        asignacion.setPrioridad(request.getPrioridad());
        asignacion.setIniciadaEn(request.getIniciadaEn());
        asignacion.setCompletadaEn(request.getCompletadaEn());
    }
}