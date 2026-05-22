package cl.hilton.tarifas.service;

import cl.hilton.tarifas.model.Descuento;
import cl.hilton.tarifas.repository.DescuentoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DescuentoService {

    private final DescuentoRepository descuentoRepository;

    public List<Descuento> obtenerDescuentos() {
        return descuentoRepository.findAll();
    }

    public Optional<Descuento> obtenerPorId(@NonNull Long id) {
        return descuentoRepository.findById(id);
    }

    public Optional<Descuento> obtenerPorCodigo(String codigoDescuento) {
        return descuentoRepository.findByCodigoDescuento(codigoDescuento);
    }

    public Descuento guardarDescuento(@NonNull Descuento descuento) {
        return descuentoRepository.save(descuento);
    }

    public void eliminarDescuento(@NonNull Long id) {
        descuentoRepository.deleteById(id);
    }

    public List<Descuento> obtenerDescuentosActivos(Boolean activo) {
        return descuentoRepository.findByActivo(activo);
    }
}