package cl.hilton.reportes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.reportes.dto.KpiRequest;
import cl.hilton.reportes.dto.KpiResponse;
import cl.hilton.reportes.mapper.KpiMapper;
import cl.hilton.reportes.model.Kpi;
import cl.hilton.reportes.repository.KpiRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KpiService {

    private final KpiRepository kpiRepository;
    private final KpiMapper kpiMapper;

    public List<KpiResponse> findAll() {
        return kpiMapper.toResponseList(kpiRepository.findAll());
    }

    public KpiResponse findById(Long id) {
        Kpi kpi = getKpiById(id);
        return kpiMapper.toResponse(kpi);
    }

    public KpiResponse findByNombre(String nombre) {
        Kpi kpi = kpiRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("KPI no encontrado con nombre: " + nombre));

        return kpiMapper.toResponse(kpi);
    }

    public List<KpiResponse> findByNombreContaining(String nombre) {
        return kpiMapper.toResponseList(kpiRepository.findByNombreContainingIgnoreCase(nombre));
    }

    public List<KpiResponse> findByPeriodo(String periodo) {
        return kpiMapper.toResponseList(kpiRepository.findByPeriodo(periodo));
    }

    public List<KpiResponse> findByUnidad(String unidad) {
        return kpiMapper.toResponseList(kpiRepository.findByUnidad(unidad));
    }

    public List<KpiResponse> findByActualizadoEn(LocalDate actualizadoEn) {
        return kpiMapper.toResponseList(kpiRepository.findByActualizadoEn(actualizadoEn));
    }

    public KpiResponse create(KpiRequest request) {
        validarNombreUnico(request.getNombre());

        Kpi kpi = kpiMapper.toEntity(request);
        kpi.setPeriodo(request.getPeriodo() != null ? request.getPeriodo() : "MENSUAL");
        kpi.setActualizadoEn(LocalDate.now());

        Kpi kpiGuardado = kpiRepository.save(kpi);

        return kpiMapper.toResponse(kpiGuardado);
    }

    public KpiResponse update(Long id, KpiRequest request) {
        Kpi kpi = getKpiById(id);
        String periodoActual = kpi.getPeriodo();

        if (!kpi.getNombre().equalsIgnoreCase(request.getNombre())) {
            validarNombreUnico(request.getNombre());
        }

        kpiMapper.updateEntity(request, kpi);
        kpi.setPeriodo(request.getPeriodo() != null ? request.getPeriodo() : periodoActual);
        kpi.setActualizadoEn(LocalDate.now());

        Kpi kpiActualizado = kpiRepository.save(kpi);

        return kpiMapper.toResponse(kpiActualizado);
    }

    public void deleteById(Long id) {
        Kpi kpi = getKpiById(id);
        kpiRepository.delete(kpi);
    }

    private Kpi getKpiById(Long id) {
        return kpiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("KPI no encontrado con id: " + id));
    }

    private void validarNombreUnico(String nombre) {
        if (kpiRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("Ya existe un KPI con nombre: " + nombre);
        }
    }
}
