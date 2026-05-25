package cl.hilton.autenticacion.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.autenticacion.dto.UsuarioRequest;
import cl.hilton.autenticacion.dto.UsuarioResponse;
import cl.hilton.autenticacion.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "rol", ignore = true)
    Usuario toEntity(UsuarioRequest request);

    @Mapping(target = "rol", source = "rol.codigo")
    UsuarioResponse toResponse(Usuario usuario);

    List<UsuarioResponse> toResponseList(List<Usuario> usuarios);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "rol", ignore = true)
    void updateEntity(UsuarioRequest request, @MappingTarget Usuario usuario);
}
