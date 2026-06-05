package cl.hilton.reportes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.reportes.dto.MetricaRequest;
import cl.hilton.reportes.dto.MetricaResponse;
import cl.hilton.reportes.mapper.MetricaMapper;
import cl.hilton.reportes.model.Metrica;
import cl.hilton.reportes.model.Reporte;
import cl.hilton.reportes.repository.MetricaRepository;
import cl.hilton.reportes.repository.ReporteRepository;
import cl.hilton.common.exception.EntityNotFoundException;
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
        Metrica metrica = getMetricaById(id);
        return metricaMapper.toResponse(metrica);
    }

    public List<MetricaResponse> findByReporte(String codigoReporte) {
        String codigo = validarTexto(codigoReporte, "codigoReporte");
        return metricaMapper.toResponseList(metricaRepository.findByReporteCodigo(codigo));
    }

    public List<MetricaResponse> findByPeriodo(LocalDate periodo) {
        LocalDate fecha = validarFecha(periodo, "periodo");
        return metricaMapper.toResponseList(metricaRepository.findByPeriodo(fecha));
    }

    public List<MetricaResponse> findByRangoFechas(LocalDate desde, LocalDate hasta) {
        LocalDate fechaDesde = validarFecha(desde, "desde");
        LocalDate fechaHasta = validarFecha(hasta, "hasta");

        if (fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a la fecha hasta");
        }

        return metricaMapper.toResponseList(metricaRepository.findByPeriodoBetween(fechaDesde, fechaHasta));
    }

    public List<MetricaResponse> findByNombreMetrica(String nombreMetrica) {
        String nombre = validarTexto(nombreMetrica, "nombreMetrica");
        return metricaMapper.toResponseList(metricaRepository.findByNombreMetrica(nombre));
    }

    public List<MetricaResponse> findByCalculadoEn(LocalDate calculadoEn) {
        LocalDate fecha = validarFecha(calculadoEn, "calculadoEn");
        return metricaMapper.toResponseList(metricaRepository.findByCalculadoEn(fecha));
    }

    @Transactional
    public MetricaResponse create(MetricaRequest request) {
        String codigoReporte = validarTexto(request.getCodigoReporte(), "codigoReporte");
        LocalDate periodo = validarFecha(request.getPeriodo(), "periodo");
        String nombreMetrica = validarTexto(request.getNombreMetrica(), "nombreMetrica");

        validarMetricaUnica(codigoReporte, periodo, nombreMetrica);

        Reporte reporte = getReporteByCodigo(codigoReporte);
        Metrica metrica = metricaMapper.toEntity(request);
        metrica.setReporte(reporte);
        metrica.setCalculadoEn(LocalDate.now());

        Metrica metricaGuardada = metricaRepository.save(metrica);

        return metricaMapper.toResponse(metricaGuardada);
    }

    @Transactional
    public MetricaResponse update(Long id, MetricaRequest request) {
        Long metricaId = validarId(id);
        String codigoReporte = validarTexto(request.getCodigoReporte(), "codigoReporte");
        LocalDate periodo = validarFecha(request.getPeriodo(), "periodo");
        String nombreMetrica = validarTexto(request.getNombreMetrica(), "nombreMetrica");

        Metrica metrica = getMetricaById(metricaId);

        if (!metrica.getReporte().getCodigo().equalsIgnoreCase(codigoReporte)
                || !metrica.getPeriodo().equals(periodo)
                || !metrica.getNombreMetrica().equalsIgnoreCase(nombreMetrica)) {
            validarMetricaUnica(codigoReporte, periodo, nombreMetrica);
        }

        Reporte reporte = getReporteByCodigo(codigoReporte);
        metricaMapper.updateEntity(request, metrica);
        metrica.setReporte(reporte);

        Metrica metricaActualizada = metricaRepository.save(metrica);

        return metricaMapper.toResponse(metricaActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long metricaId = validarId(id);
        getMetricaById(metricaId);
        metricaRepository.deleteById(metricaId);
    }

    private Metrica getMetricaById(Long id) {
        Long metricaId = validarId(id);

        return metricaRepository.findById(metricaId)
                .orElseThrow(() -> new EntityNotFoundException("Metrica no encontrada con id: " + metricaId));
    }

    private Reporte getReporteByCodigo(String codigoReporte) {
        String codigo = validarTexto(codigoReporte, "codigoReporte");

        return reporteRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con codigo: " + codigo));
    }

    private void validarMetricaUnica(String codigoReporte, LocalDate periodo, String nombreMetrica) {
        if (metricaRepository.existsByReporteCodigoAndPeriodoAndNombreMetrica(codigoReporte, periodo, nombreMetrica)) {
            throw new IllegalArgumentException("Ya existe esa metrica para el reporte y periodo indicado");
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
