package cl.hilton.huespedes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.huespedes.dto.HuespedRequest;
import cl.hilton.huespedes.dto.HuespedResponse;
import cl.hilton.huespedes.mapper.HuespedMapper;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.repository.HuespedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HuespedService {

    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;

    public List<HuespedResponse> findAll() {
        return huespedMapper.toResponseList(huespedRepository.findAll());
    }

    public HuespedResponse findById(Long id) {
        Huesped huesped = getHuespedById(id);
        return huespedMapper.toResponse(huesped);
    }

    public HuespedResponse findByEmail(String email) {
        Huesped huesped = huespedRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + email));

        return huespedMapper.toResponse(huesped);
    }

    public List<HuespedResponse> findByNombreCompleto(String nombreCompleto) {
        return huespedMapper.toResponseList(huespedRepository.findByNombreCompletoContainingIgnoreCase(nombreCompleto));
    }

    public List<HuespedResponse> findByActivo(Boolean activo) {
        return huespedMapper.toResponseList(huespedRepository.findByActivo(activo));
    }

    public List<HuespedResponse> findByTelefono(String telefono) {
        return huespedMapper.toResponseList(huespedRepository.findByTelefono(telefono));
    }

    public List<HuespedResponse> findByCreadoEn(LocalDate creadoEn) {
        return huespedMapper.toResponseList(huespedRepository.findByCreadoEn(creadoEn));
    }

    public HuespedResponse create(HuespedRequest request) {
        validarEmailUnico(request.getEmail());

        Huesped huesped = huespedMapper.toEntity(request);
        huesped.setActivo(request.getActivo() != null ? request.getActivo() : true);
        huesped.setCreadoEn(LocalDate.now());

        Huesped huespedGuardado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedGuardado);
    }

    public HuespedResponse update(Long id, HuespedRequest request) {
        Huesped huesped = getHuespedById(id);
        Boolean activoActual = huesped.getActivo();

        if (!huesped.getEmail().equalsIgnoreCase(request.getEmail())) {
            validarEmailUnico(request.getEmail());
        }

        huespedMapper.updateEntity(request, huesped);
        huesped.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        Huesped huespedActualizado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedActualizado);
    }

    public HuespedResponse cambiarActivo(Long id, Boolean activo) {
        Huesped huesped = getHuespedById(id);
        huesped.setActivo(activo);

        Huesped huespedActualizado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedActualizado);
    }

    public void deleteById(Long id) {
        Huesped huesped = getHuespedById(id);
        huespedRepository.delete(huesped);
    }

    private Huesped getHuespedById(Long id) {
        return huespedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con id: " + id));
    }

    private void validarEmailUnico(String email) {
        if (huespedRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un huesped con email: " + email);
        }
    }
}
