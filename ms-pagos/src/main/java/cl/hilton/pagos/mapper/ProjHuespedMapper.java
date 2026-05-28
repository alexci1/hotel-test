package cl.hilton.pagos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.pagos.dto.ProjHuespedRequest;
import cl.hilton.pagos.dto.ProjHuespedResponse;
import cl.hilton.pagos.model.ProjHuesped;

@Mapper(componentModel = "spring")
public interface ProjHuespedMapper {

    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "facturas", ignore = true)
    ProjHuesped toEntity(ProjHuespedRequest request);

    ProjHuespedResponse toResponse(ProjHuesped huesped);

    List<ProjHuespedResponse> toResponseList(List<ProjHuesped> huespedes);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "facturas", ignore = true)
    void updateEntity(ProjHuespedRequest request, @MappingTarget ProjHuesped huesped);
}
