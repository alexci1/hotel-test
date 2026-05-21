package cl.hilton.restaurante.service;


import cl.hilton.restaurante.dto.MesaRequest;
import cl.hilton.restaurante.dto.MesaResponse;
import cl.hilton.restaurante.model.Mesa;
import cl.hilton.restaurante.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository mesaRepository;

    public List<MesaResponse> listar() {
        return mesaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public MesaResponse buscarPorId(Integer id) {
        return toResponse(obtenerMesa(id));
    }

    public MesaResponse buscarPorNumeroMesa(String numeroMesa) {
        Mesa mesa = mesaRepository.findByNumeroMesa(numeroMesa)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        return toResponse(mesa);
    }

    public List<MesaResponse> buscarPorZona(String zona) {
        return mesaRepository.findByZona(zona).stream().map(this::toResponse).toList();
    }

    public List<MesaResponse> buscarPorDisponibilidad(Boolean disponible) {
        return mesaRepository.findByDisponible(disponible).stream().map(this::toResponse).toList();
    }

    public List<MesaResponse> buscarPorCapacidadMinima(Short capacidad) {
        return mesaRepository.findByCapacidadGreaterThanEqual(capacidad).stream().map(this::toResponse).toList();
    }

    public MesaResponse crear(MesaRequest request) {
        if (mesaRepository.existsByNumeroMesa(request.getNumeroMesa())) {
            throw new RuntimeException("Ya existe una mesa con ese número");
        }

        Mesa mesa = Mesa.builder()
                .numeroMesa(request.getNumeroMesa())
                .capacidad(request.getCapacidad())
                .zona(request.getZona())
                .disponible(request.getDisponible())
                .build();

        return toResponse(mesaRepository.save(mesa));
    }

    public MesaResponse actualizar(Integer id, MesaRequest request) {
        Mesa mesa = obtenerMesa(id);

        mesa.setNumeroMesa(request.getNumeroMesa());
        mesa.setCapacidad(request.getCapacidad());
        mesa.setZona(request.getZona());
        mesa.setDisponible(request.getDisponible());

        return toResponse(mesaRepository.save(mesa));
    }

    public MesaResponse cambiarDisponibilidad(Integer id, Boolean disponible) {
        Mesa mesa = obtenerMesa(id);
        mesa.setDisponible(disponible);
        return toResponse(mesaRepository.save(mesa));
    }

    public void eliminar(Integer id) {
        Mesa mesa = obtenerMesa(id);
        mesaRepository.delete(mesa);
    }

    private Mesa obtenerMesa(Integer id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
    }

    private MesaResponse toResponse(Mesa mesa) {
        return MesaResponse.builder()
                .id(mesa.getId())
                .numeroMesa(mesa.getNumeroMesa())
                .capacidad(mesa.getCapacidad())
                .zona(mesa.getZona())
                .disponible(mesa.getDisponible())
                .build();
    }
}
