package cl.hilton.checkin.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cl.hilton.checkin.dto.ProjHuespedRequest;
import cl.hilton.checkin.dto.ProjHuespedResponse;
import cl.hilton.checkin.model.ProjHuesped;

@Component
public class ProjHuespedMapper {

    public ProjHuesped toEntity(ProjHuespedRequest request) {
        if (request == null) {
            return null;
        }

        ProjHuesped huesped = new ProjHuesped();
        huesped.setNombreCompleto(request.getNombreCompleto());
        return huesped;
    }

    public ProjHuespedResponse toResponse(ProjHuesped huesped) {
        if (huesped == null) {
            return null;
        }

        ProjHuespedResponse response = new ProjHuespedResponse();
        response.setEmail(huesped.getEmail());
        response.setNombreCompleto(huesped.getNombreCompleto());
        response.setActualizadoEn(huesped.getActualizadoEn());
        return response;
    }

    public List<ProjHuespedResponse> toResponseList(List<ProjHuesped> huespedes) {
        if (huespedes == null) {
            return null;
        }

        return huespedes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(ProjHuespedRequest request, ProjHuesped huesped) {
        if (request == null || huesped == null) {
            return;
        }

        huesped.setNombreCompleto(request.getNombreCompleto());
    }
}
