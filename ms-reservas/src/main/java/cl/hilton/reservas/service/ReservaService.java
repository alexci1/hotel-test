package cl.hilton.reservas.service;

import cl.hilton.reservas.model.Reserva;
import cl.hilton.reservas.repository.ReservaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public List<Reserva> obtenerReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> obtenerPorId(@NonNull Long id) {
        return reservaRepository.findById(id);
    }

    public Optional<Reserva> obtenerPorCodigo(String codigoReserva) {
        return reservaRepository.findByCodigoReserva(codigoReserva);
    }

    public Reserva guardarReserva(@NonNull Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void eliminarReserva(@NonNull Long id) {
        reservaRepository.deleteById(id);
    }

    public List<Reserva> obtenerPorEstado(String estado) {
        return reservaRepository.findByEstado(estado);
    }
}