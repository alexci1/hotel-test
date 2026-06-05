package cl.hilton.autenticacion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.autenticacion.dto.RolRequest;
import cl.hilton.autenticacion.dto.RolResponse;
import cl.hilton.autenticacion.mapper.RolMapper;
import cl.hilton.autenticacion.model.Rol;
import cl.hilton.autenticacion.repository.RolRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public List<RolResponse> findAll() {
        return rolMapper.toResponseList(rolRepository.findAll());
    }

    public RolResponse findById(Long id) {
        Rol rol = getRolById(id);
        return rolMapper.toResponse(rol);
    }

    public RolResponse findByCodigo(String codigo) {
        String codigoValido = validarTexto(codigo, "codigo");

        Rol rol = rolRepository.findByCodigo(codigoValido)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con codigo: " + codigoValido));

        return rolMapper.toResponse(rol);
    }

    public List<RolResponse> findByActivo(Boolean activo) {
        Boolean estado = validarBoolean(activo, "activo");
        return rolMapper.toResponseList(rolRepository.findByActivo(estado));
    }

    @Transactional
    public RolResponse create(RolRequest request) {
        String codigo = validarTexto(request.getCodigo(), "codigo");
        validarCodigoUnico(codigo);

        Rol rol = rolMapper.toEntity(request);
        rol.setActivo(request.getActivo() != null ? request.getActivo() : true);

        Rol rolGuardado = rolRepository.save(rol);

        return rolMapper.toResponse(rolGuardado);
    }

    @Transactional
    public RolResponse update(Long id, RolRequest request) {
        Long rolId = validarId(id);
        String codigo = validarTexto(request.getCodigo(), "codigo");

        Rol rol = getRolById(rolId);
        Boolean activoActual = rol.getActivo();

        if (!rol.getCodigo().equalsIgnoreCase(codigo)) {
            validarCodigoUnico(codigo);
        }

        rolMapper.updateEntity(request, rol);
        rol.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        Rol rolActualizado = rolRepository.save(rol);

        return rolMapper.toResponse(rolActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long rolId = validarId(id);
        getRolById(rolId);
        rolRepository.deleteById(rolId);
    }

    private Rol getRolById(Long id) {
        Long rolId = validarId(id);

        return rolRepository.findById(rolId)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + rolId));
    }

    private void validarCodigoUnico(String codigo) {
        if (rolRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un rol con el codigo: " + codigo);
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
