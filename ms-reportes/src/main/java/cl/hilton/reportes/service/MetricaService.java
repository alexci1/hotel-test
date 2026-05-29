package cl.hilton.reportes.service;

import java.time.LocalDate;
import java.util.List;

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
public class MetricaService {

    private final MetricaRepository metricaRepository;
    private final ReporteRepository reporteRepository;
    private final MetricaMapper metricaMapper;

    public List<MetricaResponse> findAll() {
        return metricaMapper.toResponseList(metricaRepository.findAll());
    }

    public MetricaResponse findById(Long id) {
        Metrica metrica = getMetricaById(id);
        return metricaMapper.toResponse(metrica);
    }

    public List<MetricaResponse> findByReporte(String codigoReporte) {
        return metricaMapper.toResponseList(metricaRepository.findByReporteCodigo(codigoReporte));
    }

    public List<MetricaResponse> findByPeriodo(LocalDate periodo) {
        return metricaMapper.toResponseList(metricaRepository.findByPeriodo(periodo));
    }

    public List<MetricaResponse> findByRangoFechas(LocalDate desde, LocalDate hasta) {
        return metricaMapper.toResponseList(metricaRepository.findByPeriodoBetween(desde, hasta));
    }

    public List<MetricaResponse> findByNombreMetrica(String nombreMetrica) {
        return metricaMapper.toResponseList(metricaRepository.findByNombreMetrica(nombreMetrica));
    }

    public List<MetricaResponse> findByCalculadoEn(LocalDate calculadoEn) {
        return metricaMapper.toResponseList(metricaRepository.findByCalculadoEn(calculadoEn));
    }

    public MetricaResponse create(MetricaRequest request) {
        validarMetricaUnica(request.getCodigoReporte(), request.getPeriodo(), request.getNombreMetrica());

        Reporte reporte = reporteRepository.findByCodigo(request.getCodigoReporte())
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con codigo: " + request.getCodigoReporte()));

        Metrica metrica = metricaMapper.toEntity(request);
        metrica.setReporte(reporte);
        metrica.setCalculadoEn(LocalDate.now());

        Metrica metricaGuardada = metricaRepository.save(metrica);

        return metricaMapper.toResponse(metricaGuardada);
    }

    public MetricaResponse update(Long id, MetricaRequest request) {
        Metrica metrica = getMetricaById(id);

        if (!metrica.getReporte().getCodigo().equalsIgnoreCase(request.getCodigoReporte())
                || !metrica.getPeriodo().equals(request.getPeriodo())
                || !metrica.getNombreMetrica().equalsIgnoreCase(request.getNombreMetrica())) {
            validarMetricaUnica(request.getCodigoReporte(), request.getPeriodo(), request.getNombreMetrica());
        }

        Reporte reporte = reporteRepository.findByCodigo(request.getCodigoReporte())
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con codigo: " + request.getCodigoReporte()));

        metricaMapper.updateEntity(request, metrica);
        metrica.setReporte(reporte);

        Metrica metricaActualizada = metricaRepository.save(metrica);

        return metricaMapper.toResponse(metricaActualizada);
    }

    public void deleteById(Long id) {
        Metrica metrica = getMetricaById(id);
        metricaRepository.delete(metrica);
    }

    private Metrica getMetricaById(Long id) {
        return metricaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Metrica no encontrada con id: " + id));
    }

    private void validarMetricaUnica(String codigoReporte, LocalDate periodo, String nombreMetrica) {
        if (metricaRepository.existsByReporteCodigoAndPeriodoAndNombreMetrica(codigoReporte, periodo, nombreMetrica)) {
            throw new IllegalArgumentException("Ya existe esa metrica para el reporte y periodo indicado");
        }
    }
}
