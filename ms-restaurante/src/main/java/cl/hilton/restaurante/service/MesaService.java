package cl.hilton.restaurante.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.restaurante.dto.MesaRequest;
import cl.hilton.restaurante.dto.MesaResponse;
import cl.hilton.restaurante.mapper.MesaMapper;
import cl.hilton.restaurante.model.Mesa;
import cl.hilton.restaurante.repository.MesaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

    public List<MesaResponse> findAll() {
        return mesaMapper.toResponseList(mesaRepository.findAll());
    }

    public MesaResponse findById(Long id) {
        Mesa mesa = getMesaById(id);
        return mesaMapper.toResponse(mesa);
    }

    public MesaResponse findByNumeroMesa(String numeroMesa) {
        Mesa mesa = mesaRepository.findByNumeroMesa(numeroMesa)
                .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada con numero: " + numeroMesa));

        return mesaMapper.toResponse(mesa);
    }

    public List<MesaResponse> findByZona(String zona) {
        return mesaMapper.toResponseList(mesaRepository.findByZona(zona));
    }

    public List<MesaResponse> findByDisponible(Boolean disponible) {
        return mesaMapper.toResponseList(mesaRepository.findByDisponible(disponible));
    }

    public List<MesaResponse> findByCapacidadMinima(Integer capacidad) {
        return mesaMapper.toResponseList(mesaRepository.findByCapacidadGreaterThanEqual(capacidad));
    }

    public List<MesaResponse> findByZonaAndDisponible(String zona, Boolean disponible) {
        return mesaMapper.toResponseList(mesaRepository.findByZonaAndDisponible(zona, disponible));
    }

    public MesaResponse create(MesaRequest request) {
        validarNumeroMesaUnico(request.getNumeroMesa());

        Mesa mesa = mesaMapper.toEntity(request);
        mesa.setZona(request.getZona() != null ? request.getZona() : "SALON");
        mesa.setDisponible(request.getDisponible() != null ? request.getDisponible() : true);

        Mesa mesaGuardada = mesaRepository.save(mesa);

        return mesaMapper.toResponse(mesaGuardada);
    }

    public MesaResponse update(Long id, MesaRequest request) {
        Mesa mesa = getMesaById(id);
        String zonaActual = mesa.getZona();
        Boolean disponibleActual = mesa.getDisponible();

        if (!mesa.getNumeroMesa().equalsIgnoreCase(request.getNumeroMesa())) {
            validarNumeroMesaUnico(request.getNumeroMesa());
        }

        mesaMapper.updateEntity(request, mesa);
        mesa.setZona(request.getZona() != null ? request.getZona() : zonaActual);
        mesa.setDisponible(request.getDisponible() != null ? request.getDisponible() : disponibleActual);

        Mesa mesaActualizada = mesaRepository.save(mesa);

        return mesaMapper.toResponse(mesaActualizada);
    }

    public MesaResponse cambiarDisponibilidad(Long id, Boolean disponible) {
        Mesa mesa = getMesaById(id);
        mesa.setDisponible(disponible);

        Mesa mesaActualizada = mesaRepository.save(mesa);

        return mesaMapper.toResponse(mesaActualizada);
    }

    public void deleteById(Long id) {
        Mesa mesa = getMesaById(id);
        mesaRepository.delete(mesa);
    }

    private Mesa getMesaById(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada con id: " + id));
    }

    private void validarNumeroMesaUnico(String numeroMesa) {
        if (mesaRepository.existsByNumeroMesa(numeroMesa)) {
            throw new IllegalArgumentException("Ya existe una mesa con numero: " + numeroMesa);
        }
    }
}
