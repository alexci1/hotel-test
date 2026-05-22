package cl.hilton.huespedes.mapper;

import cl.hilton.huespedes.dto.PreferenciaRequest;
import cl.hilton.huespedes.dto.PreferenciaResponse;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.model.Preferencia;
import org.springframework.stereotype.Component;

@Component
public class PreferenciaMapper {

    public Preferencia toEntity(PreferenciaRequest request, Huesped huesped) {
        return Preferencia.builder()
                .huesped(huesped)
                .pisoPreferido(request.getPisoPreferido())
                .tipoCama(request.getTipoCama())
                .alergias(request.getAlergias())
                .observaciones(request.getObservaciones())
                .build();
    }

    public PreferenciaResponse toResponse(Preferencia preferencia) {
        return PreferenciaResponse.builder()
                .id(preferencia.getId())
                .emailHuesped(preferencia.getHuesped().getEmail())
                .nombreHuesped(preferencia.getHuesped().getNombreCompleto())
                .pisoPreferido(preferencia.getPisoPreferido())
                .tipoCama(preferencia.getTipoCama())
                .alergias(preferencia.getAlergias())
                .observaciones(preferencia.getObservaciones())
                .build();
    }

    public void updateEntity(Preferencia preferencia, PreferenciaRequest request, Huesped huesped) {
        preferencia.setHuesped(huesped);
        preferencia.setPisoPreferido(request.getPisoPreferido());
        preferencia.setTipoCama(request.getTipoCama());
        preferencia.setAlergias(request.getAlergias());
        preferencia.setObservaciones(request.getObservaciones());
    }
}