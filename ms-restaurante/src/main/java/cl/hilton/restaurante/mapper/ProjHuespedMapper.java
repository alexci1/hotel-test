package cl.hilton.restaurante.mapper;


import cl.hilton.restaurante.dto.ProjHuespedRequest;
import cl.hilton.restaurante.dto.ProjHuespedResponse;
import cl.hilton.restaurante.model.ProjHuesped;
import org.springframework.stereotype.Component;

@Component
public class ProjHuespedMapper {

    public ProjHuesped toEntity(ProjHuespedRequest request) {
        return ProjHuesped.builder()
                .email(request.getEmail())
                .nombreCompleto(request.getNombreCompleto())
                .numeroHabitacion(request.getNumeroHabitacion())
                .actualizadoEn(request.getActualizadoEn())
                .build();
    }

    public ProjHuespedResponse toResponse(ProjHuesped huesped) {
        return ProjHuespedResponse.builder()
                .email(huesped.getEmail())
                .nombreCompleto(huesped.getNombreCompleto())
                .numeroHabitacion(huesped.getNumeroHabitacion())
                .actualizadoEn(huesped.getActualizadoEn())
                .build();
    }

    public void updateEntity(ProjHuesped huesped, ProjHuespedRequest request) {
        huesped.setNombreCompleto(request.getNombreCompleto());
        huesped.setNumeroHabitacion(request.getNumeroHabitacion());
        huesped.setActualizadoEn(request.getActualizadoEn());
    }
}