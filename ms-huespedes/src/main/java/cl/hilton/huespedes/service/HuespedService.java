package cl.hilton.huespedes.service;

import cl.hilton.huespedes.dto.HuespedRequest;
import cl.hilton.huespedes.dto.HuespedResponse;
import cl.hilton.huespedes.mapper.HuespedMapper;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.repository.HuespedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HuespedService {

    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;

    public HuespedService(HuespedRepository huespedRepository, HuespedMapper huespedMapper) {
        this.huespedRepository = huespedRepository;
        this.huespedMapper = huespedMapper;
    }

    public List<HuespedResponse> listar() {
        return huespedRepository.findAll().stream()
                .map(huespedMapper::toResponse)
                .toList();
    }

    public HuespedResponse buscarPorId(Long id) {
        return huespedMapper.toResponse(obtenerHuesped(id));
    }

    public HuespedResponse buscarPorEmail(String email) {
        Huesped huesped = huespedRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));

        return huespedMapper.toResponse(huesped);
    }

    public List<HuespedResponse> buscarPorNombre(String nombre) {
        return huespedRepository.findByNombreCompletoContainingIgnoreCase(nombre).stream()
                .map(huespedMapper::toResponse)
                .toList();
    }

    public List<HuespedResponse> buscarPorActivo(Boolean activo) {
        return huespedRepository.findByActivo(activo).stream()
                .map(huespedMapper::toResponse)
                .toList();
    }

    public HuespedResponse crear(HuespedRequest request) {
        if (huespedRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un huésped con ese email");
        }

        Huesped huesped = huespedMapper.toEntity(request);

        return huespedMapper.toResponse(huespedRepository.save(huesped));
    }

    public HuespedResponse actualizar(Long id, HuespedRequest request) {
        Huesped huesped = obtenerHuesped(id);

        huespedMapper.updateEntity(huesped, request);

        return huespedMapper.toResponse(huespedRepository.save(huesped));
    }

    public void eliminar(Long id) {
        Huesped huesped = obtenerHuesped(id);
        huespedRepository.delete(huesped);
    }

    private Huesped obtenerHuesped(Long id) {
        return huespedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));
    }
}