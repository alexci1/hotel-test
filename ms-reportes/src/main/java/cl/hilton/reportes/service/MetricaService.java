package cl.hilton.reportes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.hilton.reportes.dto.MetricaRequest;
import cl.hilton.reportes.dto.MetricaResponse;
import cl.hilton.reportes.mapper.MetricaMapper;
import cl.hilton.reportes.model.Metrica;
import cl.hilton.reportes.model.Reporte;
import cl.hilton.reportes.repository.MetricaRepository;
import cl.hilton.reportes.repository.ReporteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class MetricaService {

    private final MetricaRepository metricaRepository;
    private final ReporteRepository reporteRepository;
    private final MetricaMapper metricaMapper;

    public List<MetricaResponse> findAll() {
        return metricaMapper.toResponseList(metricaRepository.findAll());
    }

    public MetricaResponse findById(Long id) {
        return metricaMapper.toResponse(getMetricaById(id));
    }

    public List<MetricaResponse> findByReporte(String codigoReporte) {
        String codigo = Objects.requireNonNull(codigoReporte);
        return metricaMapper.toResponseList(metricaRepository.findByReporteCodigo(codigo));
    }

    public List<MetricaResponse> findByPeriodo(LocalDate periodo) {
        LocalDate fecha = Objects.requireNonNull(periodo);
        return metricaMapper.toResponseList(metricaRepository.findByPeriodo(fecha));
    }

    public List<MetricaResponse> findByRangoFechas(LocalDate desde, LocalDate hasta) {
        LocalDate fechaDesde = Objects.requireNonNull(desde);
        LocalDate fechaHasta = Objects.requireNonNull(hasta);
        return metricaMapper.toResponseList(metricaRepository.findByPeriodoBetween(fechaDesde, fechaHasta));
    }

    public List<MetricaResponse> findByNombreMetrica(String nombreMetrica) {
        String nombre = Objects.requireNonNull(nombreMetrica);
        return metricaMapper.toResponseList(metricaRepository.findByNombreMetrica(nombre));
    }

    public List<MetricaResponse> findByCalculadoEn(LocalDate calculadoEn) {
        LocalDate fecha = Objects.requireNonNull(calculadoEn);
        return metricaMapper.toResponseList(metricaRepository.findByCalculadoEn(fecha));
    }

    public MetricaResponse create(MetricaRequest request) {
        String codigoReporte = Objects.requireNonNull(request.getCodigoReporte());
        LocalDate periodo = Objects.requireNonNull(request.getPeriodo());
        String nombreMetrica = Objects.requireNonNull(request.getNombreMetrica());

        validarMetricaUnica(codigoReporte, periodo, nombreMetrica);

        Reporte reporte = getReporteByCodigo(codigoReporte);
        Metrica metrica = metricaMapper.toEntity(request);
        metrica.setReporte(reporte);
        metrica.setCalculadoEn(LocalDate.now());

        Metrica saved = metricaRepository.save(Objects.requireNonNull(metrica));
        return metricaMapper.toResponse(saved);
    }

    public MetricaResponse update(Long id, MetricaRequest request) {
        Long metricaId = Objects.requireNonNull(id);
        String codigoReporte = Objects.requireNonNull(request.getCodigoReporte());
        LocalDate periodo = Objects.requireNonNull(request.getPeriodo());
        String nombreMetrica = Objects.requireNonNull(request.getNombreMetrica());

        Metrica metrica = getMetricaById(metricaId);

        if (!metrica.getReporte().getCodigo().equalsIgnoreCase(codigoReporte)
                || !metrica.getPeriodo().equals(periodo)
                || !metrica.getNombreMetrica().equalsIgnoreCase(nombreMetrica)) {
            validarMetricaUnica(codigoReporte, periodo, nombreMetrica);
        }

        Reporte reporte = getReporteByCodigo(codigoReporte);
        metricaMapper.updateEntity(request, metrica);
        metrica.setReporte(reporte);

        Metrica saved = metricaRepository.save(Objects.requireNonNull(metrica));
        return metricaMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Metrica metrica = getMetricaById(id);
        metricaRepository.delete(Objects.requireNonNull(metrica));
    }

    private Metrica getMetricaById(Long id) {
        Long metricaId = Objects.requireNonNull(id);
        return metricaRepository.findById(metricaId)
                .orElseThrow(() -> new EntityNotFoundException("Metrica no encontrada con id: " + metricaId));
    }

    private Reporte getReporteByCodigo(String codigoReporte) {
        String codigo = Objects.requireNonNull(codigoReporte);
        return reporteRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con codigo: " + codigo));
    }

    private void validarMetricaUnica(String codigoReporte, LocalDate periodo, String nombreMetrica) {
        String codigo = Objects.requireNonNull(codigoReporte);
        LocalDate fecha = Objects.requireNonNull(periodo);
        String nombre = Objects.requireNonNull(nombreMetrica);

        if (metricaRepository.existsByReporteCodigoAndPeriodoAndNombreMetrica(codigo, fecha, nombre)) {
            throw new IllegalArgumentException("Ya existe esa metrica para el reporte y periodo indicado");
        }
    }
}