package cl.hilton.checkin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.checkin.dto.ProjHuespedRequest;
import cl.hilton.checkin.dto.ProjHuespedResponse;
import cl.hilton.checkin.model.ProjHuesped;

@Mapper(componentModel = "spring")
public interface ProjHuespedMapper {

    @Mapping(target = "checkins", ignore = true)
    ProjHuesped toEntity(ProjHuespedRequest request);

    ProjHuespedResponse toResponse(ProjHuesped huesped);

    List<ProjHuespedResponse> toResponseList(List<ProjHuesped> huespedes);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "checkins", ignore = true)
    void updateEntity(ProjHuespedRequest request, @MappingTarget ProjHuesped huesped);
}
