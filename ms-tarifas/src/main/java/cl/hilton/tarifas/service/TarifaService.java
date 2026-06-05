package cl.hilton.tarifas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.tarifas.dto.TarifaRequest;
import cl.hilton.tarifas.dto.TarifaResponse;
import cl.hilton.tarifas.mapper.TarifaMapper;
import cl.hilton.tarifas.model.ProjTipoHabitacion;
import cl.hilton.tarifas.model.Tarifa;
import cl.hilton.tarifas.model.Temporada;
import cl.hilton.tarifas.repository.ProjTipoHabitacionRepository;
import cl.hilton.tarifas.repository.TarifasRepository;
import cl.hilton.tarifas.repository.TemporadaRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifasRepository tarifasRepository;
    private final TemporadaRepository temporadaRepository;
    private final ProjTipoHabitacionRepository tipoHabitacionRepository;
    private final TarifaMapper tarifaMapper;

    public List<TarifaResponse> findAll() {
        return tarifaMapper.toResponseList(tarifasRepository.findAll());
    }

    public TarifaResponse findById(Long id) {
        Tarifa tarifa = getTarifaById(id);
        return tarifaMapper.toResponse(tarifa);
    }

    public TarifaResponse findByTemporadaAndTipoHabitacion(String codigoTemporada, String tipoHabitacion) {
        Tarifa tarifa = tarifasRepository.findByTemporadaCodigoAndTipoHabitacionCodigo(codigoTemporada, tipoHabitacion)
                .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada para temporada y tipo de habitacion indicados"));

        return tarifaMapper.toResponse(tarifa);
    }

    public List<TarifaResponse> findByCodigoTemporada(String codigoTemporada) {
        return tarifaMapper.toResponseList(tarifasRepository.findByTemporadaCodigo(codigoTemporada));
    }

    public List<TarifaResponse> findByTipoHabitacion(String tipoHabitacion) {
        return tarifaMapper.toResponseList(tarifasRepository.findByTipoHabitacionCodigo(tipoHabitacion));
    }

    public List<TarifaResponse> findByActiva(Boolean activa) {
        return tarifaMapper.toResponseList(tarifasRepository.findByActiva(activa));
    }

    public List<TarifaResponse> findByIncluyeDesayuno(Boolean incluyeDesayuno) {
        return tarifaMapper.toResponseList(tarifasRepository.findByIncluyeDesayuno(incluyeDesayuno));
    }

    public List<TarifaResponse> findByTipoHabitacionAndActiva(String tipoHabitacion, Boolean activa) {
        return tarifaMapper.toResponseList(tarifasRepository.findByTipoHabitacionCodigoAndActiva(tipoHabitacion, activa));
    }

    public TarifaResponse create(TarifaRequest request) {
        validarTarifaUnica(request.getCodigoTemporada(), request.getCodigoTipoHabitacion());

        Temporada temporada = temporadaRepository.findByCodigo(request.getCodigoTemporada())
                .orElseThrow(() -> new EntityNotFoundException("Temporada no encontrada con codigo: " + request.getCodigoTemporada()));

        ProjTipoHabitacion tipoHabitacion = tipoHabitacionRepository.findByCodigo(request.getCodigoTipoHabitacion())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion proyectado no encontrado: " + request.getCodigoTipoHabitacion()));

        Tarifa tarifa = tarifaMapper.toEntity(request);
        tarifa.setTemporada(temporada);
        tarifa.setTipoHabitacion(tipoHabitacion);
        tarifa.setIncluyeDesayuno(request.getIncluyeDesayuno() != null ? request.getIncluyeDesayuno() : false);
        tarifa.setActiva(request.getActiva() != null ? request.getActiva() : true);

        Tarifa tarifaGuardada = tarifasRepository.save(tarifa);

        return tarifaMapper.toResponse(tarifaGuardada);
    }

    public TarifaResponse update(Long id, TarifaRequest request) {
        Tarifa tarifa = getTarifaById(id);
        Boolean incluyeDesayunoActual = tarifa.getIncluyeDesayuno();
        Boolean activaActual = tarifa.getActiva();

        if (!tarifa.getTemporada().getCodigo().equalsIgnoreCase(request.getCodigoTemporada())
                || !tarifa.getTipoHabitacion().getCodigo().equalsIgnoreCase(request.getCodigoTipoHabitacion())) {
            validarTarifaUnica(request.getCodigoTemporada(), request.getCodigoTipoHabitacion());
        }

        Temporada temporada = temporadaRepository.findByCodigo(request.getCodigoTemporada())
                .orElseThrow(() -> new EntityNotFoundException("Temporada no encontrada con codigo: " + request.getCodigoTemporada()));

        ProjTipoHabitacion tipoHabitacion = tipoHabitacionRepository.findByCodigo(request.getCodigoTipoHabitacion())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de habitacion proyectado no encontrado: " + request.getCodigoTipoHabitacion()));

        tarifaMapper.updateEntity(request, tarifa);
        tarifa.setTemporada(temporada);
        tarifa.setTipoHabitacion(tipoHabitacion);
        tarifa.setIncluyeDesayuno(request.getIncluyeDesayuno() != null ? request.getIncluyeDesayuno() : incluyeDesayunoActual);
        tarifa.setActiva(request.getActiva() != null ? request.getActiva() : activaActual);

        Tarifa tarifaActualizada = tarifasRepository.save(tarifa);

        return tarifaMapper.toResponse(tarifaActualizada);
    }

    public void deleteById(Long id) {
        Tarifa tarifa = getTarifaById(id);
        tarifasRepository.delete(tarifa);
    }

    private Tarifa getTarifaById(Long id) {
        return tarifasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada con id: " + id));
    }

    private void validarTarifaUnica(String codigoTemporada, String tipoHabitacion) {
        if (tarifasRepository.existsByTemporadaCodigoAndTipoHabitacionCodigo(codigoTemporada, tipoHabitacion)) {
            throw new IllegalArgumentException("Ya existe una tarifa para esa temporada y tipo de habitacion");
        }
    }
}
