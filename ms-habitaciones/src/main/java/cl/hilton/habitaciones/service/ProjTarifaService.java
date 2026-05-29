package cl.hilton.habitaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.habitaciones.client.TarifaLookupClient;
import cl.hilton.habitaciones.dto.ProjTarifaRequest;
import cl.hilton.habitaciones.dto.ProjTarifaResponse;
import cl.hilton.habitaciones.dto.TarifaHabitacionResponse;
import cl.hilton.habitaciones.mapper.ProjTarifaMapper;
import cl.hilton.habitaciones.model.ProjTarifa;
import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.repository.ProjTarifaRepository;
import cl.hilton.habitaciones.repository.TipoHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjTarifaService {

    private final ProjTarifaRepository projTarifaRepository;
    private final TipoHabitacionRepository tipoHabitacionRepository;
    private final ProjTarifaMapper projTarifaMapper;
    private final TarifaLookupClient tarifaLookupClient;

    public List<ProjTarifaResponse> findAll() {
        return projTarifaMapper.toResponseList(projTarifaRepository.findAll());
    }

    public ProjTarifaResponse findByTipoHabitacion(String tipoHabitacion) {
        ProjTarifa tarifa = getTarifaByTipoHabitacion(tipoHabitacion);
        return projTarifaMapper.toResponse(tarifa);
    }

    public ProjTarifaResponse create(ProjTarifaRequest request) {
        validarTipoHabitacionExiste(request.getTipoHabitacion());
        validarTarifaUnica(request.getTipoHabitacion());

        ProjTarifa tarifa = projTarifaMapper.toEntity(request);
        tarifa.setActualizadoEn(LocalDate.now());

        ProjTarifa tarifaGuardada = projTarifaRepository.save(tarifa);

        return projTarifaMapper.toResponse(tarifaGuardada);
    }

    public ProjTarifaResponse update(String tipoHabitacion, ProjTarifaRequest request) {
        ProjTarifa tarifa = getTarifaByTipoHabitacion(tipoHabitacion);

        projTarifaMapper.updateEntity(request, tarifa);
        tarifa.setActualizadoEn(LocalDate.now());

        ProjTarifa tarifaActualizada = projTarifaRepository.save(tarifa);

        return projTarifaMapper.toResponse(tarifaActualizada);
    }

    public ProjTarifaResponse sincronizarPorTipoHabitacion(String tipoHabitacion) {
        List<TarifaHabitacionResponse> tarifasExternas = tarifaLookupClient.buscarPorTipoHabitacionYActiva(tipoHabitacion, true);

        if (tarifasExternas.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron tarifas activas para tipo habitacion: " + tipoHabitacion);
        }

        TarifaHabitacionResponse externa = tarifasExternas.get(0);
        ProjTarifa tarifa = projTarifaRepository.findByTipoHabitacion(externa.getCodigoTipoHabitacion())
                .orElseGet(ProjTarifa::new);

        tarifa.setTipoHabitacion(externa.getCodigoTipoHabitacion());
        tarifa.setPrecioBaseUsd(externa.getPrecioNocheUsd());
        tarifa.setActualizadoEn(LocalDate.now());

        ProjTarifa tarifaGuardada = projTarifaRepository.save(tarifa);

        return projTarifaMapper.toResponse(tarifaGuardada);
    }

    public void deleteByTipoHabitacion(String tipoHabitacion) {
        ProjTarifa tarifa = getTarifaByTipoHabitacion(tipoHabitacion);
        projTarifaRepository.delete(tarifa);
    }

    private ProjTarifa getTarifaByTipoHabitacion(String tipoHabitacion) {
        return projTarifaRepository.findByTipoHabitacion(tipoHabitacion)
                .orElseThrow(() -> new EntityNotFoundException("Tarifa proyectada no encontrada para tipo habitacion: " + tipoHabitacion));
    }

    private void validarTarifaUnica(String tipoHabitacion) {
        if (projTarifaRepository.existsByTipoHabitacion(tipoHabitacion)) {
            throw new IllegalArgumentException("Ya existe una tarifa proyectada para tipo habitacion: " + tipoHabitacion);
        }
    }

    private void validarTipoHabitacionExiste(String codigo) {
        TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion no encontrado con codigo: " + codigo));

        if (!Boolean.TRUE.equals(tipoHabitacion.getActivo())) {
            throw new IllegalArgumentException("El tipo de habitacion no esta activo: " + codigo);
        }
    }
}
