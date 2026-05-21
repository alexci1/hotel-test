package cl.hilton.reportes.service;

import cl.hilton.reportes.dto.MetricaRequest;
import cl.hilton.reportes.dto.MetricaResponse;
import cl.hilton.reportes.model.Metrica;
import cl.hilton.reportes.model.Reporte;
import cl.hilton.reportes.repository.MetricaRepository;
import cl.hilton.reportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricaService {

    private final MetricaRepository metricaRepository;
    private final ReporteRepository reporteRepository;

    public List<MetricaResponse> listar() {
        return metricaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public MetricaResponse buscarPorId(Integer id) {
        return toResponse(obtenerMetrica(id));
    }

    public List<MetricaResponse> buscarPorReporte(String codigoReporte) {
        return metricaRepository.findByReporteCodigo(codigoReporte).stream().map(this::toResponse).toList();
    }

    public List<MetricaResponse> buscarPorPeriodo(LocalDate periodo) {
        return metricaRepository.findByPeriodo(periodo).stream().map(this::toResponse).toList();
    }

    public List<MetricaResponse> buscarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return metricaRepository.findByPeriodoBetween(desde, hasta).stream().map(this::toResponse).toList();
    }

    public List<MetricaResponse> buscarPorNombreMetrica(String nombreMetrica) {
        return metricaRepository.findByNombreMetrica(nombreMetrica).stream().map(this::toResponse).toList();
    }

    public MetricaResponse crear(MetricaRequest request) {
        if (metricaRepository.existsByReporteCodigoAndPeriodoAndNombreMetrica(
                request.getCodigoReporte(), request.getPeriodo(), request.getNombreMetrica())) {
            throw new RuntimeException("Ya existe esa métrica para el reporte y período indicado");
        }

        Reporte reporte = reporteRepository.findByCodigo(request.getCodigoReporte())
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        Metrica metrica = Metrica.builder()
                .reporte(reporte)
                .periodo(request.getPeriodo())
                .nombreMetrica(request.getNombreMetrica())
                .valor(request.getValor())
                .unidad(request.getUnidad())
                .calculadoEn(request.getCalculadoEn() != null ? request.getCalculadoEn() : OffsetDateTime.now())
                .build();

        return toResponse(metricaRepository.save(metrica));
    }

    public MetricaResponse actualizar(Integer id, MetricaRequest request) {
        Metrica metrica = obtenerMetrica(id);
        Reporte reporte = reporteRepository.findByCodigo(request.getCodigoReporte())
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        metrica.setReporte(reporte);
        metrica.setPeriodo(request.getPeriodo());
        metrica.setNombreMetrica(request.getNombreMetrica());
        metrica.setValor(request.getValor());
        metrica.setUnidad(request.getUnidad());
        metrica.setCalculadoEn(request.getCalculadoEn() != null ? request.getCalculadoEn() : metrica.getCalculadoEn());

        return toResponse(metricaRepository.save(metrica));
    }

    public void eliminar(Integer id) {
        Metrica metrica = obtenerMetrica(id);
        metricaRepository.delete(metrica);
    }

    private Metrica obtenerMetrica(Integer id) {
        return metricaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Métrica no encontrada"));
    }

    private MetricaResponse toResponse(Metrica metrica) {
        return MetricaResponse.builder()
                .id(metrica.getId())
                .codigoReporte(metrica.getReporte().getCodigo())
                .nombreReporte(metrica.getReporte().getNombre())
                .periodo(metrica.getPeriodo())
                .nombreMetrica(metrica.getNombreMetrica())
                .valor(metrica.getValor())
                .unidad(metrica.getUnidad())
                .calculadoEn(metrica.getCalculadoEn())
                .build();
    }
}