package cl.hilton.notificaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.notificaciones.dto.PlantillaRequest;
import cl.hilton.notificaciones.dto.PlantillaResponse;
import cl.hilton.notificaciones.mapper.PlantillaMapper;
import cl.hilton.notificaciones.model.Plantilla;
import cl.hilton.notificaciones.repository.PlantillaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
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
        String codigoValido = validarTexto(codigo, "codigo");

        Plantilla plantilla = plantillaRepository.findByCodigo(codigoValido)
                .orElseThrow(() -> new EntityNotFoundException("Plantilla no encontrada con codigo: " + codigoValido));

        return plantillaMapper.toResponse(plantilla);
    }

    public List<PlantillaResponse> findByCanal(String canal) {
        String canalValido = validarTexto(canal, "canal");
        return plantillaMapper.toResponseList(plantillaRepository.findByCanal(canalValido));
    }

    public List<PlantillaResponse> findByActiva(Boolean activa) {
        Boolean estado = validarBoolean(activa, "activa");
        return plantillaMapper.toResponseList(plantillaRepository.findByActiva(estado));
    }

    @Transactional
    public PlantillaResponse create(PlantillaRequest request) {
        String codigo = validarTexto(request.getCodigo(), "codigo");
        validarCodigoUnico(codigo);

        Plantilla plantilla = plantillaMapper.toEntity(request);
        plantilla.setActiva(request.getActiva() != null ? request.getActiva() : true);

        Plantilla plantillaGuardada = plantillaRepository.save(plantilla);

        return plantillaMapper.toResponse(plantillaGuardada);
    }

    @Transactional
    public PlantillaResponse update(Long id, PlantillaRequest request) {
        Long plantillaId = validarId(id);
        String codigo = validarTexto(request.getCodigo(), "codigo");

        Plantilla plantilla = getPlantillaById(plantillaId);
        Boolean activaActual = plantilla.getActiva();

        if (!plantilla.getCodigo().equalsIgnoreCase(codigo)) {
            validarCodigoUnico(codigo);
        }

        plantillaMapper.updateEntity(request, plantilla);
        plantilla.setActiva(request.getActiva() != null ? request.getActiva() : activaActual);

        Plantilla plantillaActualizada = plantillaRepository.save(plantilla);

        return plantillaMapper.toResponse(plantillaActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long plantillaId = validarId(id);
        getPlantillaById(plantillaId);
        plantillaRepository.deleteById(plantillaId);
    }

    private Plantilla getPlantillaById(Long id) {
        Long plantillaId = validarId(id);

        return plantillaRepository.findById(plantillaId)
                .orElseThrow(() -> new EntityNotFoundException("Plantilla no encontrada con id: " + plantillaId));
    }

    private void validarCodigoUnico(String codigo) {
        if (plantillaRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe una plantilla con el codigo: " + codigo);
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private Boolean validarBoolean(Boolean valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
