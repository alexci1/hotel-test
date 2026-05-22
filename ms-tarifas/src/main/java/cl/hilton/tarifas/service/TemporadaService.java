package cl.hilton.tarifas.service;

import cl.hilton.tarifas.model.Temporada;
import cl.hilton.tarifas.repository.TemporadaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemporadaService {

    private final TemporadaRepository temporadaRepository;

    public List<Temporada> obtenerTemporadas() {
        return temporadaRepository.findAll();
    }

    public Optional<Temporada> obtenerPorId(@NonNull Long id) {
        return temporadaRepository.findById(id);
    }

    public Optional<Temporada> obtenerPorCodigo(String codigo) {
        return temporadaRepository.findByCodigo(codigo);
    }

    public Temporada guardarTemporada(@NonNull Temporada temporada) {
        return temporadaRepository.save(temporada);
    }

    public void eliminarTemporada(@NonNull Long id) {
        temporadaRepository.deleteById(id);
    }

    public List<Temporada> buscarPorNombre(String nombre) {
        return temporadaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Temporada> obtenerAntesDe(LocalDate fecha) {
        return temporadaRepository.findByFechaInicioBefore(fecha);
    }

    public List<Temporada> obtenerDespuesDe(LocalDate fecha) {
        return temporadaRepository.findByFechaFinAfter(fecha);
    }
}