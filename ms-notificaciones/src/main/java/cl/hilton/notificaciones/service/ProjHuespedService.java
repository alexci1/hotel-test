package cl.hilton.notificaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.notificaciones.client.HuespedClient;
import cl.hilton.notificaciones.dto.ProjHuespedRequest;
import cl.hilton.notificaciones.dto.ProjHuespedResponse;
import cl.hilton.notificaciones.mapper.ProjHuespedMapper;
import cl.hilton.notificaciones.model.ProjHuesped;
import cl.hilton.notificaciones.repository.ProjHuespedRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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

    public List<ProjHuespedResponse> findByNombreCompleto(String nombreCompleto) {
        return huespedMapper.toResponseList(huespedRepository.findByNombreCompletoContainingIgnoreCase(nombreCompleto));
    }
    @Transactional
    public ProjHuespedResponse create(ProjHuespedRequest request) {
        validarEmailUnico(request.getEmail());

        ProjHuesped huesped = huespedMapper.toEntity(request);
        huesped.setActualizadoEn(LocalDate.now());

        ProjHuesped huespedGuardado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedGuardado);
    }
    @Transactional
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
        huesped.setActualizadoEn(LocalDate.now());

        ProjHuesped huespedGuardado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedGuardado);
    }
    @Transactional
    public void deleteByEmail(String email) {
        ProjHuesped huesped = getHuespedByEmail(email);
        huespedRepository.delete(huesped);
    }

    private ProjHuesped getHuespedByEmail(String email) {
        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + email));
    }

    private void validarEmailUnico(String email) {
        if (huespedRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un huesped proyectado con email: " + email);
        }
    }
}
