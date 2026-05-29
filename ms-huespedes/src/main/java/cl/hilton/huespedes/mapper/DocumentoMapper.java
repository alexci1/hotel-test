package cl.hilton.huespedes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.huespedes.dto.DocumentoRequest;
import cl.hilton.huespedes.dto.DocumentoResponse;
import cl.hilton.huespedes.model.Documento;

@Mapper(componentModel = "spring")
public interface DocumentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    Documento toEntity(DocumentoRequest request);

    @Mapping(target = "emailHuesped", source = "huesped.email")
    @Mapping(target = "nombreHuesped", source = "huesped.nombreCompleto")
    DocumentoResponse toResponse(Documento documento);

    List<DocumentoResponse> toResponseList(List<Documento> documentos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    void updateEntity(DocumentoRequest request, @MappingTarget Documento documento);
}
