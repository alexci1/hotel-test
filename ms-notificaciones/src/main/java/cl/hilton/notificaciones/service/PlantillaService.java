package cl.hilton.notificaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.notificaciones.dto.PlantillaRequest;
import cl.hilton.notificaciones.dto.PlantillaResponse;
import cl.hilton.notificaciones.mapper.PlantillaMapper;
import cl.hilton.notificaciones.model.Plantilla;
import cl.hilton.notificaciones.repository.PlantillaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlantillaService {

    private final PlantillaRepository plantillaRepository;
    private final PlantillaMapper plantillaMapper;

    public List<PlantillaResponse> findAll() {
        return plantillaMapper.toResponseList(plantillaRepository.findAll());
    }

    public PlantillaResponse findById(Long id) {
        Plantilla plantilla = getPlantillaById(id);
        return plantillaMapper.toResponse(plantilla);
    }

    public PlantillaResponse findByCodigo(String codigo) {
        Plantilla plantilla = plantillaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Plantilla no encontrada con codigo: " + codigo));

        return plantillaMapper.toResponse(plantilla);
    }

    public List<PlantillaResponse> findByCanal(String canal) {
        return plantillaMapper.toResponseList(plantillaRepository.findByCanal(canal));
    }

    public List<PlantillaResponse> findByActiva(Boolean activa) {
        return plantillaMapper.toResponseList(plantillaRepository.findByActiva(activa));
    }
    @Transactional
    public PlantillaResponse create(PlantillaRequest request) {
        validarCodigoUnico(request.getCodigo());

        Plantilla plantilla = plantillaMapper.toEntity(request);
        plantilla.setActiva(request.getActiva() != null ? request.getActiva() : true);

        Plantilla plantillaGuardada = plantillaRepository.save(plantilla);

        return plantillaMapper.toResponse(plantillaGuardada);
    }
    @Transactional
    public PlantillaResponse update(Long id, PlantillaRequest request) {
        Plantilla plantilla = getPlantillaById(id);
        Boolean activaActual = plantilla.getActiva();

        if (!plantilla.getCodigo().equalsIgnoreCase(request.getCodigo())) {
            validarCodigoUnico(request.getCodigo());
        }

        plantillaMapper.updateEntity(request, plantilla);
        plantilla.setActiva(request.getActiva() != null ? request.getActiva() : activaActual);

        Plantilla plantillaActualizada = plantillaRepository.save(plantilla);

        return plantillaMapper.toResponse(plantillaActualizada);
    }
    @Transactional
    public void deleteById(Long id) {
        Plantilla plantilla = getPlantillaById(id);
        plantillaRepository.delete(plantilla);
    }

    private Plantilla getPlantillaById(Long id) {
        return plantillaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plantilla no encontrada con id: " + id));
    }

    private void validarCodigoUnico(String codigo) {
        if (plantillaRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe una plantilla con el codigo: " + codigo);
        }
    }
}
