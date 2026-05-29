package cl.hilton.reportes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.hilton.reportes.dto.KpiRequest;
import cl.hilton.reportes.dto.KpiResponse;
import cl.hilton.reportes.mapper.KpiMapper;
import cl.hilton.reportes.model.Kpi;
import cl.hilton.reportes.model.Reporte;
import cl.hilton.reportes.repository.KpiRepository;
import cl.hilton.reportes.repository.ReporteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KpiService {

    private final KpiRepository kpiRepository;
    private final ReporteRepository reporteRepository;
    private final KpiMapper kpiMapper;

    public List<KpiResponse> findAll() {
        return kpiMapper.toResponseList(kpiRepository.findAll());
    }

    public KpiResponse findById(Long id) {
        return kpiMapper.toResponse(getKpiById(id));
    }

    public KpiResponse findByNombre(String nombre) {
        Kpi kpi = kpiRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("KPI no encontrado con nombre: " + nombre));

        return kpiMapper.toResponse(kpi);
    }

    public List<KpiResponse> findByReporte(String codigoReporte) {
        return kpiMapper.toResponseList(kpiRepository.findByReporteCodigo(codigoReporte));
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

        Reporte reporte = getReporteByCodigo(request.getCodigoReporte());
        Kpi kpi = kpiMapper.toEntity(request);
        kpi.setReporte(reporte);
        kpi.setPeriodo(request.getPeriodo() != null ? request.getPeriodo() : "MENSUAL");
        kpi.setActualizadoEn(LocalDate.now());

        Kpi saved = kpiRepository.save(Objects.requireNonNull(kpi));
        return kpiMapper.toResponse(saved);
    }

    public KpiResponse update(Long id, KpiRequest request) {
        Kpi kpi = getKpiById(id);
        String periodoActual = kpi.getPeriodo();

        if (!kpi.getNombre().equalsIgnoreCase(request.getNombre())) {
            validarNombreUnico(request.getNombre());
        }

        Reporte reporte = getReporteByCodigo(request.getCodigoReporte());
        kpiMapper.updateEntity(request, kpi);
        kpi.setReporte(reporte);
        kpi.setPeriodo(request.getPeriodo() != null ? request.getPeriodo() : periodoActual);
        kpi.setActualizadoEn(LocalDate.now());

        Kpi saved = kpiRepository.save(Objects.requireNonNull(kpi));
        return kpiMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Kpi kpi = getKpiById(id);
        kpiRepository.delete(Objects.requireNonNull(kpi));
    }

    private Kpi getKpiById(Long id) {
        return kpiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("KPI no encontrado con id: " + id));
    }

    private Reporte getReporteByCodigo(String codigoReporte) {
        return reporteRepository.findByCodigo(codigoReporte)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con codigo: " + codigoReporte));
    }

    private void validarNombreUnico(String nombre) {
        if (kpiRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("Ya existe un KPI con nombre: " + nombre);
        }
    }
}