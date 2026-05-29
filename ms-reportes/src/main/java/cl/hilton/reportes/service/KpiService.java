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
@SuppressWarnings("null")
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
        String nombreKpi = Objects.requireNonNull(nombre);
        Kpi kpi = kpiRepository.findByNombre(nombreKpi)
                .orElseThrow(() -> new EntityNotFoundException("KPI no encontrado con nombre: " + nombreKpi));

        return kpiMapper.toResponse(kpi);
    }

    public List<KpiResponse> findByReporte(String codigoReporte) {
        String codigo = Objects.requireNonNull(codigoReporte);
        return kpiMapper.toResponseList(kpiRepository.findByReporteCodigo(codigo));
    }

    public List<KpiResponse> findByNombreContaining(String nombre) {
        String nombreKpi = Objects.requireNonNull(nombre);
        return kpiMapper.toResponseList(kpiRepository.findByNombreContainingIgnoreCase(nombreKpi));
    }

    public List<KpiResponse> findByPeriodo(String periodo) {
        String periodoKpi = Objects.requireNonNull(periodo);
        return kpiMapper.toResponseList(kpiRepository.findByPeriodo(periodoKpi));
    }

    public List<KpiResponse> findByUnidad(String unidad) {
        String unidadKpi = Objects.requireNonNull(unidad);
        return kpiMapper.toResponseList(kpiRepository.findByUnidad(unidadKpi));
    }

    public List<KpiResponse> findByActualizadoEn(LocalDate actualizadoEn) {
        LocalDate fecha = Objects.requireNonNull(actualizadoEn);
        return kpiMapper.toResponseList(kpiRepository.findByActualizadoEn(fecha));
    }

    public KpiResponse create(KpiRequest request) {
        String nombre = Objects.requireNonNull(request.getNombre());
        String codigoReporte = Objects.requireNonNull(request.getCodigoReporte());
        validarNombreUnico(nombre);

        Reporte reporte = getReporteByCodigo(codigoReporte);
        Kpi kpi = kpiMapper.toEntity(request);
        kpi.setReporte(reporte);
        kpi.setPeriodo(request.getPeriodo() != null ? request.getPeriodo() : "MENSUAL");
        kpi.setActualizadoEn(LocalDate.now());

        Kpi saved = kpiRepository.save(Objects.requireNonNull(kpi));
        return kpiMapper.toResponse(saved);
    }

    public KpiResponse update(Long id, KpiRequest request) {
        Long kpiId = Objects.requireNonNull(id);
        String nombre = Objects.requireNonNull(request.getNombre());
        String codigoReporte = Objects.requireNonNull(request.getCodigoReporte());

        Kpi kpi = getKpiById(kpiId);
        String periodoActual = kpi.getPeriodo();

        if (!kpi.getNombre().equalsIgnoreCase(nombre)) {
            validarNombreUnico(nombre);
        }

        Reporte reporte = getReporteByCodigo(codigoReporte);
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
        Long kpiId = Objects.requireNonNull(id);
        return kpiRepository.findById(kpiId)
                .orElseThrow(() -> new EntityNotFoundException("KPI no encontrado con id: " + kpiId));
    }

    private Reporte getReporteByCodigo(String codigoReporte) {
        String codigo = Objects.requireNonNull(codigoReporte);
        return reporteRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con codigo: " + codigo));
    }

    private void validarNombreUnico(String nombre) {
        String nombreKpi = Objects.requireNonNull(nombre);
        if (kpiRepository.existsByNombre(nombreKpi)) {
            throw new IllegalArgumentException("Ya existe un KPI con nombre: " + nombreKpi);
        }
    }
}