package cl.hilton.restaurante.mapper;


import cl.hilton.restaurante.dto.MesaRequest;
import cl.hilton.restaurante.dto.MesaResponse;
import cl.hilton.restaurante.model.Mesa;
import org.springframework.stereotype.Component;

@Component
public class MesaMapper {

    public Mesa toEntity(MesaRequest request) {
        return Mesa.builder()
                .numeroMesa(request.getNumeroMesa())
                .capacidad(request.getCapacidad())
                .zona(request.getZona())
                .disponible(request.getDisponible())
                .build();
    }

    public MesaResponse toResponse(Mesa mesa) {
        return MesaResponse.builder()
                .id(mesa.getId())
                .numeroMesa(mesa.getNumeroMesa())
                .capacidad(mesa.getCapacidad())
                .zona(mesa.getZona())
                .disponible(mesa.getDisponible())
                .build();
    }

    public void updateEntity(Mesa mesa, MesaRequest request) {
        mesa.setNumeroMesa(request.getNumeroMesa());
        mesa.setCapacidad(request.getCapacidad());
        mesa.setZona(request.getZona());
        mesa.setDisponible(request.getDisponible());
    }
}
