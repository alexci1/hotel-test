package cl.hilton.huespedes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.huespedes.dto.PreferenciaRequest;
import cl.hilton.huespedes.dto.PreferenciaResponse;
import cl.hilton.huespedes.mapper.PreferenciaMapper;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.model.Preferencia;
import cl.hilton.huespedes.repository.HuespedRepository;
import cl.hilton.huespedes.repository.PreferenciaRepository;
import cl.hilton.common.exception.EntityNotFoundException;
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
        String email = validarTexto(emailHuesped, "emailHuesped");

        Preferencia preferencia = preferenciaRepository.findByHuespedEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Preferencia no encontrada para huesped: " + email));

        return preferenciaMapper.toResponse(preferencia);
    }

    public List<PreferenciaResponse> findByTipoCama(String tipoCama) {
        String tipo = validarTexto(tipoCama, "tipoCama");
        return preferenciaMapper.toResponseList(preferenciaRepository.findByTipoCama(tipo));
    }

    public List<PreferenciaResponse> findByPisoPreferido(Integer pisoPreferido) {
        Integer piso = validarInteger(pisoPreferido, "pisoPreferido");
        return preferenciaMapper.toResponseList(preferenciaRepository.findByPisoPreferido(piso));
    }

    @Transactional
    public PreferenciaResponse create(PreferenciaRequest request) {
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");

        if (preferenciaRepository.existsByHuespedEmail(emailHuesped)) {
            throw new IllegalArgumentException("Ya existe una preferencia para el huesped: " + emailHuesped);
        }

        Huesped huesped = getHuespedByEmail(emailHuesped);

        Preferencia preferencia = preferenciaMapper.toEntity(request);
        preferencia.setHuesped(huesped);

        Preferencia preferenciaGuardada = preferenciaRepository.save(preferencia);

        return preferenciaMapper.toResponse(preferenciaGuardada);
    }

    @Transactional
    public PreferenciaResponse update(Long id, PreferenciaRequest request) {
        Long preferenciaId = validarId(id);
        Preferencia preferencia = getPreferenciaById(preferenciaId);

        if (request.getEmailHuesped() != null && !request.getEmailHuesped().isBlank()) {
            String emailHuesped = request.getEmailHuesped();

            if (!preferencia.getHuesped().getEmail().equalsIgnoreCase(emailHuesped)) {
                if (preferenciaRepository.existsByHuespedEmail(emailHuesped)) {
                    throw new IllegalArgumentException("Ya existe una preferencia para el huesped: " + emailHuesped);
                }

                Huesped huesped = getHuespedByEmail(emailHuesped);
                preferencia.setHuesped(huesped);
            }
        }

        preferenciaMapper.updateEntity(request, preferencia);

        Preferencia preferenciaActualizada = preferenciaRepository.save(preferencia);

        return preferenciaMapper.toResponse(preferenciaActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long preferenciaId = validarId(id);
        getPreferenciaById(preferenciaId);
        preferenciaRepository.deleteById(preferenciaId);
    }

    private Preferencia getPreferenciaById(Long id) {
        Long preferenciaId = validarId(id);

        return preferenciaRepository.findById(preferenciaId)
                .orElseThrow(() -> new EntityNotFoundException("Preferencia no encontrada con id: " + preferenciaId));
    }

    private Huesped getHuespedByEmail(String email) {
        String emailValido = validarTexto(email, "email");

        return huespedRepository.findByEmail(emailValido)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + emailValido));
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private Integer validarInteger(Integer valor, String campo) {
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
