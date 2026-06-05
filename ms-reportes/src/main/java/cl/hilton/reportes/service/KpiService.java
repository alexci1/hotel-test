package cl.hilton.reportes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.reportes.dto.KpiRequest;
import cl.hilton.reportes.dto.KpiResponse;
import cl.hilton.reportes.mapper.KpiMapper;
import cl.hilton.reportes.model.Kpi;
import cl.hilton.reportes.model.Reporte;
import cl.hilton.reportes.repository.KpiRepository;
import cl.hilton.reportes.repository.ReporteRepository;
import cl.hilton.common.exception.EntityNotFoundException;
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
        Kpi kpi = getKpiById(id);
        return kpiMapper.toResponse(kpi);
    }

    public KpiResponse findByNombre(String nombre) {
        String nombreKpi = validarTexto(nombre, "nombre");

        Kpi kpi = kpiRepository.findByNombre(nombreKpi)
                .orElseThrow(() -> new EntityNotFoundException("KPI no encontrado con nombre: " + nombreKpi));

        return kpiMapper.toResponse(kpi);
    }

    public List<KpiResponse> findByReporte(String codigoReporte) {
        String codigo = validarTexto(codigoReporte, "codigoReporte");
        return kpiMapper.toResponseList(kpiRepository.findByReporteCodigo(codigo));
    }

    public List<KpiResponse> findByNombreContaining(String nombre) {
        String nombreKpi = validarTexto(nombre, "nombre");
        return kpiMapper.toResponseList(kpiRepository.findByNombreContainingIgnoreCase(nombreKpi));
    }

    public List<KpiResponse> findByPeriodo(String periodo) {
        String periodoKpi = validarTexto(periodo, "periodo");
        return kpiMapper.toResponseList(kpiRepository.findByPeriodo(periodoKpi));
    }

    public List<KpiResponse> findByUnidad(String unidad) {
        String unidadKpi = validarTexto(unidad, "unidad");
        return kpiMapper.toResponseList(kpiRepository.findByUnidad(unidadKpi));
    }

    public List<KpiResponse> findByActualizadoEn(LocalDate actualizadoEn) {
        LocalDate fecha = validarFecha(actualizadoEn, "actualizadoEn");
        return kpiMapper.toResponseList(kpiRepository.findByActualizadoEn(fecha));
    }

    @Transactional
    public KpiResponse create(KpiRequest request) {
        String nombre = validarTexto(request.getNombre(), "nombre");
        String codigoReporte = validarTexto(request.getCodigoReporte(), "codigoReporte");

        validarNombreUnico(nombre);

        Reporte reporte = getReporteByCodigo(codigoReporte);
        Kpi kpi = kpiMapper.toEntity(request);
        kpi.setReporte(reporte);
        kpi.setPeriodo(request.getPeriodo() != null ? request.getPeriodo() : "MENSUAL");
        kpi.setActualizadoEn(LocalDate.now());

        Kpi kpiGuardado = kpiRepository.save(kpi);

        return kpiMapper.toResponse(kpiGuardado);
    }

    @Transactional
    public KpiResponse update(Long id, KpiRequest request) {
        Long kpiId = validarId(id);
        String nombre = validarTexto(request.getNombre(), "nombre");
        String codigoReporte = validarTexto(request.getCodigoReporte(), "codigoReporte");

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

        Kpi kpiActualizado = kpiRepository.save(kpi);

        return kpiMapper.toResponse(kpiActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long kpiId = validarId(id);
        getKpiById(kpiId);
        kpiRepository.deleteById(kpiId);
    }

    private Kpi getKpiById(Long id) {
        Long kpiId = validarId(id);

        return kpiRepository.findById(kpiId)
                .orElseThrow(() -> new EntityNotFoundException("KPI no encontrado con id: " + kpiId));
    }

    private Reporte getReporteByCodigo(String codigoReporte) {
        String codigo = validarTexto(codigoReporte, "codigoReporte");

        return reporteRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con codigo: " + codigo));
    }

    private void validarNombreUnico(String nombre) {
        if (kpiRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("Ya existe un KPI con nombre: " + nombre);
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

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
