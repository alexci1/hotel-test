package cl.hilton.reportes.mapper;


import cl.hilton.reportes.dto.MetricaRequest;
import cl.hilton.reportes.dto.MetricaResponse;
import cl.hilton.reportes.model.Metrica;
import cl.hilton.reportes.model.Reporte;
import org.springframework.stereotype.Component;

@Component
public class MetricaMapper {

    public Metrica toEntity(MetricaRequest request, Reporte reporte) {
        return Metrica.builder()
                .reporte(reporte)
                .periodo(request.getPeriodo())
                .nombreMetrica(request.getNombreMetrica())
                .valor(request.getValor())
                .unidad(request.getUnidad())
                .calculadoEn(request.getCalculadoEn())
                .build();
    }

    public MetricaResponse toResponse(Metrica metrica) {
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

    public void updateEntity(Metrica metrica, MetricaRequest request, Reporte reporte) {
        metrica.setReporte(reporte);
        metrica.setPeriodo(request.getPeriodo());
        metrica.setNombreMetrica(request.getNombreMetrica());
        metrica.setValor(request.getValor());
        metrica.setUnidad(request.getUnidad());
        metrica.setCalculadoEn(request.getCalculadoEn());
    }
}
