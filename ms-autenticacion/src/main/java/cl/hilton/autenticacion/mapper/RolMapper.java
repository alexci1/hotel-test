package cl.hilton.autenticacion.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.autenticacion.dto.RolRequest;
import cl.hilton.autenticacion.dto.RolResponse;
import cl.hilton.autenticacion.model.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {

    @Mapping(target = "id", ignore = true)
    Rol toEntity(RolRequest request);

    RolResponse toResponse(Rol rol);

    List<RolResponse> toResponseList(List<Rol> roles);

    @Mapping(target = "id", ignore = true)
    void updateEntity(RolRequest request, @MappingTarget Rol rol);
}
