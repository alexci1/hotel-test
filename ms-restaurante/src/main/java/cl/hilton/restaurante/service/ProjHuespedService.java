package cl.hilton.restaurante.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.restaurante.client.HuespedClient;
import cl.hilton.restaurante.dto.ProjHuespedRequest;
import cl.hilton.restaurante.dto.ProjHuespedResponse;
import cl.hilton.restaurante.mapper.ProjHuespedMapper;
import cl.hilton.restaurante.model.ProjHuesped;
import cl.hilton.restaurante.repository.ProjHuespedRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProjHuespedService {

    private final ProjHuespedRepository huespedRepository;
    private final ProjHuespedMapper huespedMapper;
    private final HuespedClient huespedClient;

    public List<ProjHuespedResponse> findAll() {
        return huespedMapper.toResponseList(huespedRepository.findAll());
    }

    public ProjHuespedResponse findByEmail(String email) {
        ProjHuesped huesped = getHuespedByEmail(email);
        return huespedMapper.toResponse(huesped);
    }

    public List<ProjHuespedResponse> findByNumeroHabitacion(String numeroHabitacion) {
        return huespedMapper.toResponseList(huespedRepository.findByNumeroHabitacion(numeroHabitacion));
    }

    public List<ProjHuespedResponse> findByNombreCompleto(String nombreCompleto) {
        return huespedMapper.toResponseList(huespedRepository.findByNombreCompletoContainingIgnoreCase(nombreCompleto));
    }

    public ProjHuespedResponse create(ProjHuespedRequest request) {
        validarEmailUnico(request.getEmail());

        ProjHuesped huesped = huespedMapper.toEntity(request);
        huesped.setActualizadoEn(LocalDate.now());

        ProjHuesped huespedGuardado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedGuardado);
    }

    public ProjHuespedResponse update(String email, ProjHuespedRequest request) {
        ProjHuesped huesped = getHuespedByEmail(email);

        huespedMapper.updateEntity(request, huesped);
        huesped.setActualizadoEn(LocalDate.now());

        ProjHuesped huespedActualizado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedActualizado);
    }

    public ProjHuespedResponse sincronizarPorEmail(String email) {
        ProjHuespedResponse externo = huespedClient.buscarPorEmail(email);
        ProjHuesped huesped = huespedRepository.findByEmail(externo.getEmail())
                .orElseGet(ProjHuesped::new);

        huesped.setEmail(externo.getEmail());
        huesped.setNombreCompleto(externo.getNombreCompleto());
        huesped.setNumeroHabitacion(externo.getNumeroHabitacion());
        huesped.setActualizadoEn(LocalDate.now());

        ProjHuesped huespedGuardado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedGuardado);
    }

    public void deleteByEmail(String email) {
        ProjHuesped huesped = getHuespedByEmail(email);
        huespedRepository.delete(huesped);
    }

    private ProjHuesped getHuespedByEmail(String email) {
        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped proyectado no encontrado con email: " + email));
    }

    private void validarEmailUnico(String email) {
        if (huespedRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un huesped proyectado con email: " + email);
        }
    }
}
