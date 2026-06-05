package cl.hilton.pagos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.pagos.client.HuespedClient;
import cl.hilton.pagos.dto.ProjHuespedRequest;
import cl.hilton.pagos.dto.ProjHuespedResponse;
import cl.hilton.pagos.mapper.ProjHuespedMapper;
import cl.hilton.pagos.model.ProjHuesped;
import cl.hilton.pagos.repository.ProjHuespedRepository;
import cl.hilton.common.exception.EntityNotFoundException;
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
        String nombre = validarTexto(nombreCompleto, "nombreCompleto");
        return huespedMapper.toResponseList(huespedRepository.findByNombreCompletoContainingIgnoreCase(nombre));
    }

    @Transactional
    public ProjHuespedResponse create(ProjHuespedRequest request) {
        String email = validarTexto(request.getEmail(), "email");
        validarEmailUnico(email);

        ProjHuesped huesped = huespedMapper.toEntity(request);
        huesped.setActualizadoEn(LocalDate.now());

        ProjHuesped huespedGuardado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedGuardado);
    }

    @Transactional
    public ProjHuespedResponse update(String email, ProjHuespedRequest request) {
        String emailValido = validarTexto(email, "email");
        ProjHuesped huesped = getHuespedByEmail(emailValido);

        huespedMapper.updateEntity(request, huesped);
        huesped.setEmail(emailValido);
        huesped.setActualizadoEn(LocalDate.now());

        ProjHuesped huespedActualizado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedActualizado);
    }

    @Transactional
    public ProjHuespedResponse sincronizarPorEmail(String email) {
        String emailValido = validarTexto(email, "email");

        ProjHuespedResponse externo = huespedClient.buscarPorEmail(emailValido);
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
        String emailValido = validarTexto(email, "email");
        getHuespedByEmail(emailValido);
        huespedRepository.deleteById(emailValido);
    }

    private ProjHuesped getHuespedByEmail(String email) {
        String emailValido = validarTexto(email, "email");

        return huespedRepository.findByEmail(emailValido)
                .orElseThrow(() -> new EntityNotFoundException("Huesped proyectado no encontrado con email: " + emailValido));
    }

    private void validarEmailUnico(String email) {
        if (huespedRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un huesped proyectado con email: " + email);
        }
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
