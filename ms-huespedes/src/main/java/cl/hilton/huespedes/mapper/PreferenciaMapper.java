package cl.hilton.huespedes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.huespedes.dto.PreferenciaRequest;
import cl.hilton.huespedes.dto.PreferenciaResponse;
import cl.hilton.huespedes.model.Preferencia;

@Mapper(componentModel = "spring")
public interface PreferenciaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    Preferencia toEntity(PreferenciaRequest request);

    @Mapping(target = "emailHuesped", source = "huesped.email")
    @Mapping(target = "nombreHuesped", source = "huesped.nombreCompleto")
    PreferenciaResponse toResponse(Preferencia preferencia);

    List<PreferenciaResponse> toResponseList(List<Preferencia> preferencias);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    void updateEntity(PreferenciaRequest request, @MappingTarget Preferencia preferencia);
}
