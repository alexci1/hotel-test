package cl.hilton.huespedes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.huespedes.dto.HuespedRequest;
import cl.hilton.huespedes.dto.HuespedResponse;
import cl.hilton.huespedes.mapper.HuespedMapper;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.repository.HuespedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
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
        String emailValido = validarTexto(email, "email");

        Huesped huesped = huespedRepository.findByEmail(emailValido)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + emailValido));

        return huespedMapper.toResponse(huesped);
    }

    public List<HuespedResponse> findByNombreCompleto(String nombreCompleto) {
        String nombre = validarTexto(nombreCompleto, "nombreCompleto");
        return huespedMapper.toResponseList(huespedRepository.findByNombreCompletoContainingIgnoreCase(nombre));
    }

    public List<HuespedResponse> findByActivo(Boolean activo) {
        Boolean estado = validarBoolean(activo, "activo");
        return huespedMapper.toResponseList(huespedRepository.findByActivo(estado));
    }

    public List<HuespedResponse> findByTelefono(String telefono) {
        String telefonoValido = validarTexto(telefono, "telefono");
        return huespedMapper.toResponseList(huespedRepository.findByTelefono(telefonoValido));
    }

    public List<HuespedResponse> findByCreadoEn(LocalDate creadoEn) {
        LocalDate fecha = validarFecha(creadoEn, "creadoEn");
        return huespedMapper.toResponseList(huespedRepository.findByCreadoEn(fecha));
    }

    @Transactional
    public HuespedResponse create(HuespedRequest request) {
        String email = validarTexto(request.getEmail(), "email");
        validarEmailUnico(email);

        Huesped huesped = huespedMapper.toEntity(request);
        huesped.setActivo(request.getActivo() != null ? request.getActivo() : true);
        huesped.setCreadoEn(LocalDate.now());

        Huesped huespedGuardado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedGuardado);
    }

    @Transactional
    public HuespedResponse update(Long id, HuespedRequest request) {
        Long huespedId = validarId(id);
        String email = validarTexto(request.getEmail(), "email");

        Huesped huesped = getHuespedById(huespedId);
        Boolean activoActual = huesped.getActivo();

        if (!huesped.getEmail().equalsIgnoreCase(email)) {
            validarEmailUnico(email);
        }

        huespedMapper.updateEntity(request, huesped);
        huesped.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        Huesped huespedActualizado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedActualizado);
    }

    @Transactional
    public HuespedResponse cambiarActivo(Long id, Boolean activo) {
        Long huespedId = validarId(id);
        Boolean estado = validarBoolean(activo, "activo");

        Huesped huesped = getHuespedById(huespedId);
        huesped.setActivo(estado);

        Huesped huespedActualizado = huespedRepository.save(huesped);

        return huespedMapper.toResponse(huespedActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long huespedId = validarId(id);
        getHuespedById(huespedId);
        huespedRepository.deleteById(huespedId);
    }

    private Huesped getHuespedById(Long id) {
        Long huespedId = validarId(id);

        return huespedRepository.findById(huespedId)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con id: " + huespedId));
    }

    private void validarEmailUnico(String email) {
        if (huespedRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un huesped con email: " + email);
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private LocalDate validarFecha(LocalDate valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
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
