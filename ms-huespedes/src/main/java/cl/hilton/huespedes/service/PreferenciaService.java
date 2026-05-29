package cl.hilton.huespedes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.huespedes.dto.PreferenciaRequest;
import cl.hilton.huespedes.dto.PreferenciaResponse;
import cl.hilton.huespedes.mapper.PreferenciaMapper;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.model.Preferencia;
import cl.hilton.huespedes.repository.HuespedRepository;
import cl.hilton.huespedes.repository.PreferenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreferenciaService {

    private final PreferenciaRepository preferenciaRepository;
    private final HuespedRepository huespedRepository;
    private final PreferenciaMapper preferenciaMapper;

    public List<PreferenciaResponse> findAll() {
        return preferenciaMapper.toResponseList(preferenciaRepository.findAll());
    }

    public PreferenciaResponse findById(Long id) {
        Preferencia preferencia = getPreferenciaById(id);
        return preferenciaMapper.toResponse(preferencia);
    }

    public PreferenciaResponse findByEmailHuesped(String emailHuesped) {
        Preferencia preferencia = preferenciaRepository.findByHuespedEmail(emailHuesped)
                .orElseThrow(() -> new EntityNotFoundException("Preferencia no encontrada para huesped: " + emailHuesped));

        return preferenciaMapper.toResponse(preferencia);
    }

    public List<PreferenciaResponse> findByTipoCama(String tipoCama) {
        return preferenciaMapper.toResponseList(preferenciaRepository.findByTipoCama(tipoCama));
    }

    public List<PreferenciaResponse> findByPisoPreferido(Integer pisoPreferido) {
        return preferenciaMapper.toResponseList(preferenciaRepository.findByPisoPreferido(pisoPreferido));
    }

    public PreferenciaResponse create(PreferenciaRequest request) {
        if (preferenciaRepository.existsByHuespedEmail(request.getEmailHuesped())) {
            throw new IllegalArgumentException("Ya existe una preferencia para el huesped: " + request.getEmailHuesped());
        }

        Huesped huesped = getHuespedByEmail(request.getEmailHuesped());

        Preferencia preferencia = preferenciaMapper.toEntity(request);
        preferencia.setHuesped(huesped);

        Preferencia preferenciaGuardada = preferenciaRepository.save(preferencia);

        return preferenciaMapper.toResponse(preferenciaGuardada);
    }

    public PreferenciaResponse update(Long id, PreferenciaRequest request) {
        Preferencia preferencia = getPreferenciaById(id);

        if (request.getEmailHuesped() != null
                && !preferencia.getHuesped().getEmail().equalsIgnoreCase(request.getEmailHuesped())) {
            if (preferenciaRepository.existsByHuespedEmail(request.getEmailHuesped())) {
                throw new IllegalArgumentException("Ya existe una preferencia para el huesped: " + request.getEmailHuesped());
            }

            Huesped huesped = getHuespedByEmail(request.getEmailHuesped());
            preferencia.setHuesped(huesped);
        }

        preferenciaMapper.updateEntity(request, preferencia);

        Preferencia preferenciaActualizada = preferenciaRepository.save(preferencia);

        return preferenciaMapper.toResponse(preferenciaActualizada);
    }

    public void deleteById(Long id) {
        Preferencia preferencia = getPreferenciaById(id);
        preferenciaRepository.delete(preferencia);
    }

    private Preferencia getPreferenciaById(Long id) {
        return preferenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Preferencia no encontrada con id: " + id));
    }

    private Huesped getHuespedByEmail(String email) {
        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + email));
    }
}
