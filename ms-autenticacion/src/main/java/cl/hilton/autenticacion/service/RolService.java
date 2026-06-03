package cl.hilton.autenticacion.service;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import cl.hilton.autenticacion.dto.RolRequest;
import cl.hilton.autenticacion.dto.RolResponse;
import cl.hilton.autenticacion.mapper.RolMapper;
import cl.hilton.autenticacion.model.Rol;
import cl.hilton.autenticacion.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public List<RolResponse> findAll() {
        return rolMapper.toResponseList(rolRepository.findAll());
    }

    public RolResponse findById(@NonNull Long id) {
        Rol rol = getRolById(id);
        return rolMapper.toResponse(rol);
    }

    public RolResponse findByCodigo(String codigo) {
        Rol rol = rolRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con codigo: " + codigo));

        return rolMapper.toResponse(rol);
    }

    public List<RolResponse> findByActivo(Boolean activo) {
        return rolMapper.toResponseList(rolRepository.findByActivo(activo));
    }

    @Transactional
    public RolResponse create(RolRequest request) {
        validarCodigoUnico(request.getCodigo());

        Rol rol = rolMapper.toEntity(request);
        rol.setActivo(request.getActivo() != null ? request.getActivo() : true);

        Rol rolGuardado = rolRepository.save(rol);

        return rolMapper.toResponse(rolGuardado);
    }

    @Transactional
    public RolResponse update(@NonNull Long id, RolRequest request) {
        Rol rol = getRolById(id);
        Boolean activoActual = rol.getActivo();

        if (!rol.getCodigo().equalsIgnoreCase(request.getCodigo())) {
            validarCodigoUnico(request.getCodigo());
        }

        rolMapper.updateEntity(request, rol);
        rol.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        Rol rolActualizado = rolRepository.save(rol);

        return rolMapper.toResponse(rolActualizado);
    }

    @Transactional
    public void deleteById(@NonNull Long id) {
        getRolById(id);
        rolRepository.deleteById(id);
    }

    private Rol getRolById(@NonNull Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));
    }

    private void validarCodigoUnico(String codigo) {
        if (rolRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un rol con el codigo: " + codigo);
        }
    }
}