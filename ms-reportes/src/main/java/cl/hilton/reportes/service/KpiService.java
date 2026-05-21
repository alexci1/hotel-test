package cl.hilton.reportes.service;


import cl.hilton.reportes.dto.KpiRequest;
import cl.hilton.reportes.dto.KpiResponse;
import cl.hilton.reportes.model.Kpi;
import cl.hilton.reportes.repository.KpiRepository;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KpiService {

    private final KpiRepository kpiRepository;

    public List<KpiResponse> listar() {
        return kpiRepository.findAll().stream().map(this::toResponse).toList();
    }

    public KpiResponse buscarPorId(Integer id) {
        return toResponse(obtenerKpi(id));
    }

    public KpiResponse buscarPorNombreExacto(String nombre) {
        Kpi kpi = kpiRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("KPI no encontrado"));
        return toResponse(kpi);
    }

    public List<KpiResponse> buscarPorNombre(String nombre) {
        return kpiRepository.findByNombreContainingIgnoreCase(nombre).stream().map(this::toResponse).toList();
    }

    public List<KpiResponse> buscarPorPeriodo(String periodo) {
        return kpiRepository.findByPeriodo(periodo).stream().map(this::toResponse).toList();
    }

    public List<KpiResponse> buscarPorUnidad(String unidad) {
        return kpiRepository.findByUnidad(unidad).stream().map(this::toResponse).toList();
    }

    public KpiResponse crear(KpiRequest request) {
        if (kpiRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un KPI con ese nombre");
        }

        Kpi kpi = Kpi.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .valorActual(request.getValorActual())
                .valorObjetivo(request.getValorObjetivo())
                .unidad(request.getUnidad())
                .periodo(request.getPeriodo())
                .actualizadoEn(request.getActualizadoEn() != null ? request.getActualizadoEn() : OffsetDateTime.now())
                .build();

        return toResponse(kpiRepository.save(kpi));
    }

    public KpiResponse actualizar(Integer id, KpiRequest request) {
        Kpi kpi = obtenerKpi(id);

        kpi.setNombre(request.getNombre());
        kpi.setDescripcion(request.getDescripcion());
        kpi.setValorActual(request.getValorActual());
        kpi.setValorObjetivo(request.getValorObjetivo());
        kpi.setUnidad(request.getUnidad());
        kpi.setPeriodo(request.getPeriodo());
        kpi.setActualizadoEn(request.getActualizadoEn() != null ? request.getActualizadoEn() : OffsetDateTime.now());

        return toResponse(kpiRepository.save(kpi));
    }

    public void eliminar(Integer id) {
        Kpi kpi = obtenerKpi(id);
        kpiRepository.delete(kpi);
    }

    private Kpi obtenerKpi(Integer id) {
        return kpiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KPI no encontrado"));
    }

    private KpiResponse toResponse(Kpi kpi) {
        return KpiResponse.builder()
                .id(kpi.getId())
                .nombre(kpi.getNombre())
                .descripcion(kpi.getDescripcion())
                .valorActual(kpi.getValorActual())
                .valorObjetivo(kpi.getValorObjetivo())
                .unidad(kpi.getUnidad())
                .periodo(kpi.getPeriodo())
                .actualizadoEn(kpi.getActualizadoEn())
                .build();
    }
}
