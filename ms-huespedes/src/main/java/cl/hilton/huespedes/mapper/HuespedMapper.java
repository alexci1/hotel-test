package cl.hilton.huespedes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.huespedes.dto.HuespedRequest;
import cl.hilton.huespedes.dto.HuespedResponse;
import cl.hilton.huespedes.model.Huesped;

@Mapper(componentModel = "spring")
public interface HuespedMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "documentos", ignore = true)
    @Mapping(target = "preferencia", ignore = true)
    Huesped toEntity(HuespedRequest request);

    HuespedResponse toResponse(Huesped huesped);

    List<HuespedResponse> toResponseList(List<Huesped> huespedes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "documentos", ignore = true)
    @Mapping(target = "preferencia", ignore = true)
    void updateEntity(HuespedRequest request, @MappingTarget Huesped huesped);
}
