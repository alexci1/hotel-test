package cl.hilton.autenticacion.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.autenticacion.dto.SesionRequest;
import cl.hilton.autenticacion.dto.SesionResponse;
import cl.hilton.autenticacion.model.Sesion;

@Mapper(componentModel = "spring")
public interface SesionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Sesion toEntity(SesionRequest request);

    @Mapping(target = "usuarioEmail", source = "usuario.email")
    SesionResponse toResponse(Sesion sesion);

    List<SesionResponse> toResponseList(List<Sesion> sesiones);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(SesionRequest request, @MappingTarget Sesion sesion);
}
