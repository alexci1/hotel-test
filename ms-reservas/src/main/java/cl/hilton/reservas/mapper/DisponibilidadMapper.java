package cl.hilton.reservas.mapper;

import cl.hilton.reservas.dto.DisponibilidadRequest;
import cl.hilton.reservas.dto.DisponibilidadResponse;
import cl.hilton.reservas.model.Disponibilidad;
import cl.hilton.reservas.model.ProjHabitacion;

public class DisponibilidadMapper {

    public static Disponibilidad toEntity(
            DisponibilidadRequest request,
            ProjHabitacion habitacion
    ) {

        return Disponibilidad.builder()
                .habitacion(habitacion)
                .fecha(request.getFecha())
                .disponible(request.getDisponible())
                .build();
    }

    public static DisponibilidadResponse toResponse(Disponibilidad disponibilidad) {

        DisponibilidadResponse response = new DisponibilidadResponse();

        response.setId(disponibilidad.getId());
        response.setNumeroHabitacion(
                disponibilidad.getHabitacion().getNumeroHabitacion()
        );
        response.setFecha(disponibilidad.getFecha());
        response.setDisponible(disponibilidad.getDisponible());

        return response;
    }
}