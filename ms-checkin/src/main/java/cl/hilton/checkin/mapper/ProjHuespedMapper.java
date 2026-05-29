package cl.hilton.checkin.mapper;

import cl.hilton.checkin.dto.ProjHuespedRequest;
import cl.hilton.checkin.dto.ProjHuespedResponse;
import cl.hilton.checkin.model.ProjHuesped;
import org.springframework.stereotype.Component;

@Component
public class ProjHuespedMapper {

    public ProjHuesped toEntity(ProjHuespedRequest request) {
        return ProjHuesped.builder()
                .email(request.getEmail())
                .nombreCompleto(request.getNombreCompleto())
                .actualizadoEn(request.getActualizadoEn())
                .build();
    }

    public ProjHuespedResponse toResponse(ProjHuesped huesped) {
        return ProjHuespedResponse.builder()
                .email(huesped.getEmail())
                .nombreCompleto(huesped.getNombreCompleto())
                .actualizadoEn(huesped.getActualizadoEn())
                .build();
    }

    public void updateEntity(ProjHuesped huesped, ProjHuespedRequest request) {
        huesped.setNombreCompleto(request.getNombreCompleto());
        huesped.setActualizadoEn(request.getActualizadoEn());
    }
}
