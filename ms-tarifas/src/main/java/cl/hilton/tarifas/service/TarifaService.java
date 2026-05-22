package cl.hilton.tarifas.service;

import cl.hilton.tarifas.model.Tarifa;
import cl.hilton.tarifas.repository.TarifasRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifasRepository tarifasRepository;

    public List<Tarifa> obtenerTarifas() {
        return tarifasRepository.findAll();
    }

    public Optional<Tarifa> obtenerPorId(@NonNull Long id) {
        return tarifasRepository.findById(id);
    }

    public Tarifa guardarTarifa(@NonNull Tarifa tarifa) {
        return tarifasRepository.save(tarifa);
    }

    public void eliminarTarifa(@NonNull Long id) {
        tarifasRepository.deleteById(id);
    }

    public List<Tarifa> obtenerTarifasActivas(Boolean activa) {
        return tarifasRepository.findByActiva(activa);
    }
}