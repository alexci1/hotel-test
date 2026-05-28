package cl.hilton.pagos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.pagos.dto.CargoRequest;
import cl.hilton.pagos.dto.CargoResponse;
import cl.hilton.pagos.model.Cargo;

@Mapper(componentModel = "spring")
public interface CargoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factura", ignore = true)
    @Mapping(target = "registradoEn", ignore = true)
    Cargo toEntity(CargoRequest request);

    @Mapping(target = "numeroFactura", source = "factura.numeroFactura")
    CargoResponse toResponse(Cargo cargo);

    List<CargoResponse> toResponseList(List<Cargo> cargos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factura", ignore = true)
    @Mapping(target = "registradoEn", ignore = true)
    void updateEntity(CargoRequest request, @MappingTarget Cargo cargo);
}
