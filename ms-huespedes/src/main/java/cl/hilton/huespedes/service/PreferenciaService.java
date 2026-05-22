package cl.hilton.huespedes.service;

import cl.hilton.huespedes.dto.PreferenciaRequest;
import cl.hilton.huespedes.dto.PreferenciaResponse;
import cl.hilton.huespedes.mapper.PreferenciaMapper;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.model.Preferencia;
import cl.hilton.huespedes.repository.HuespedRepository;
import cl.hilton.huespedes.repository.PreferenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenciaService {

    private final PreferenciaRepository preferenciaRepository;
    private final HuespedRepository huespedRepository;
    private final PreferenciaMapper preferenciaMapper;

    public PreferenciaService(
            PreferenciaRepository preferenciaRepository,
            HuespedRepository huespedRepository,
            PreferenciaMapper preferenciaMapper
    ) {
        this.preferenciaRepository = preferenciaRepository;
        this.huespedRepository = huespedRepository;
        this.preferenciaMapper = preferenciaMapper;
    }

    public List<PreferenciaResponse> listar() {
        return preferenciaRepository.findAll().stream()
                .map(preferenciaMapper::toResponse)
                .toList();
    }

    public PreferenciaResponse buscarPorId(Long id) {
        return preferenciaMapper.toResponse(obtenerPreferencia(id));
    }

    public PreferenciaResponse buscarPorEmailHuesped(String emailHuesped) {
        Preferencia preferencia = preferenciaRepository.findByHuespedEmail(emailHuesped)
                .orElseThrow(() -> new RuntimeException("Preferencia no encontrada"));

        return preferenciaMapper.toResponse(preferencia);
    }

    public List<PreferenciaResponse> buscarPorTipoCama(String tipoCama) {
        return preferenciaRepository.findByTipoCama(tipoCama).stream()
                .map(preferenciaMapper::toResponse)
                .toList();
    }

    public PreferenciaResponse crear(PreferenciaRequest request) {
        if (preferenciaRepository.existsByHuespedEmail(request.getEmailHuesped())) {
            throw new RuntimeException("Ya existe una preferencia para ese huésped");
        }

        Huesped huesped = obtenerHuespedPorEmail(request.getEmailHuesped());
        Preferencia preferencia = preferenciaMapper.toEntity(request, huesped);

        return preferenciaMapper.toResponse(preferenciaRepository.save(preferencia));
    }

    public PreferenciaResponse actualizar(Long id, PreferenciaRequest request) {
        Preferencia preferencia = obtenerPreferencia(id);
        Huesped huesped = obtenerHuespedPorEmail(request.getEmailHuesped());

        preferenciaMapper.updateEntity(preferencia, request, huesped);

        return preferenciaMapper.toResponse(preferenciaRepository.save(preferencia));
    }

    public void eliminar(Long id) {
        Preferencia preferencia = obtenerPreferencia(id);
        preferenciaRepository.delete(preferencia);
    }

    private Preferencia obtenerPreferencia(Long id) {
        return preferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preferencia no encontrada"));
    }

    private Huesped obtenerHuespedPorEmail(String email) {
        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));
    }
}