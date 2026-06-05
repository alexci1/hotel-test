package cl.hilton.habitaciones.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.habitaciones.client.TarifaLookupClient;
import cl.hilton.habitaciones.dto.ProjTarifaRequest;
import cl.hilton.habitaciones.dto.ProjTarifaResponse;
import cl.hilton.habitaciones.dto.TarifaHabitacionResponse;
import cl.hilton.habitaciones.mapper.ProjTarifaMapper;
import cl.hilton.habitaciones.model.ProjTarifa;
import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.repository.ProjTarifaRepository;
import cl.hilton.habitaciones.repository.TipoHabitacionRepository;
import cl.hilton.common.exception.EntityNotFoundException;

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

    @Transactional
    public ProjTarifaResponse create(ProjTarifaRequest request) {
        String tipoHabitacion = validarTexto(request.getTipoHabitacion(), "tipoHabitacion");

        validarTipoHabitacionExiste(tipoHabitacion);
        validarTarifaUnica(tipoHabitacion);

        ProjTarifa tarifa = projTarifaMapper.toEntity(request);
        tarifa.setActualizadoEn(LocalDate.now());

        ProjTarifa tarifaGuardada = projTarifaRepository.save(tarifa);

        return projTarifaMapper.toResponse(tarifaGuardada);
    }

    @Transactional
    public ProjTarifaResponse update(String tipoHabitacion, ProjTarifaRequest request) {
        String tipo = validarTexto(tipoHabitacion, "tipoHabitacion");
        ProjTarifa tarifa = getTarifaByTipoHabitacion(tipo);

        projTarifaMapper.updateEntity(request, tarifa);
        tarifa.setTipoHabitacion(tipo);
        tarifa.setActualizadoEn(LocalDate.now());

        ProjTarifa tarifaActualizada = projTarifaRepository.save(tarifa);

        return projTarifaMapper.toResponse(tarifaActualizada);
    }

    @Transactional
    public ProjTarifaResponse sincronizarPorTipoHabitacion(String tipoHabitacion) {
        String tipo = validarTexto(tipoHabitacion, "tipoHabitacion");

        List<TarifaHabitacionResponse> tarifasExternas = tarifaLookupClient.buscarPorTipoHabitacionYActiva(tipo, true);

        if (tarifasExternas.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron tarifas activas para tipo habitacion: " + tipo);
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

    @Transactional
    public void deleteByTipoHabitacion(String tipoHabitacion) {
        String tipo = validarTexto(tipoHabitacion, "tipoHabitacion");
        getTarifaByTipoHabitacion(tipo);
        projTarifaRepository.deleteById(tipo);
    }

    private ProjTarifa getTarifaByTipoHabitacion(String tipoHabitacion) {
        String tipo = validarTexto(tipoHabitacion, "tipoHabitacion");

        return projTarifaRepository.findByTipoHabitacion(tipo)
                .orElseThrow(() -> new EntityNotFoundException("Tarifa proyectada no encontrada para tipo habitacion: " + tipo));
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

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
