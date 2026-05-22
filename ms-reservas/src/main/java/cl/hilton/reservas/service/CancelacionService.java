package cl.hilton.reservas.service;

import cl.hilton.reservas.model.Cancelacion;
import cl.hilton.reservas.repository.CancelacionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CancelacionService {

    private final CancelacionRepository cancelacionRepository;

    public List<Cancelacion> obtenerCancelaciones() {
        return cancelacionRepository.findAll();
    }

    public Optional<Cancelacion> obtenerPorId(@NonNull Long id) {
        return cancelacionRepository.findById(id);
    }

    public Optional<Cancelacion> obtenerPorCodigoReserva(String codigoReserva) {
        return cancelacionRepository.findByReservaCodigoReserva(codigoReserva);
    }

    public Cancelacion guardarCancelacion(@NonNull Cancelacion cancelacion) {
        return cancelacionRepository.save(cancelacion);
    }

    public void eliminarCancelacion(@NonNull Long id) {
        cancelacionRepository.deleteById(id);
    }
}