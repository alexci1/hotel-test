package cl.hilton.pagos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.pagos.dto.CargoRequest;
import cl.hilton.pagos.dto.CargoResponse;
import cl.hilton.pagos.mapper.CargoMapper;
import cl.hilton.pagos.model.Cargo;
import cl.hilton.pagos.model.Factura;
import cl.hilton.pagos.repository.CargoRepository;
import cl.hilton.pagos.repository.FacturaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;
    private final FacturaRepository facturaRepository;
    private final CargoMapper cargoMapper;

    public List<CargoResponse> findAll() {
        return cargoMapper.toResponseList(cargoRepository.findAll());
    }

    public CargoResponse findById(Long id) {
        Cargo cargo = getCargoById(id);
        return cargoMapper.toResponse(cargo);
    }

    public List<CargoResponse> findByNumeroFactura(String numeroFactura) {
        return cargoMapper.toResponseList(cargoRepository.findByFacturaNumeroFactura(numeroFactura));
    }

    public List<CargoResponse> findByOrigen(String origen) {
        return cargoMapper.toResponseList(cargoRepository.findByOrigen(origen));
    }

    public List<CargoResponse> findByRegistradoEn(LocalDate registradoEn) {
        return cargoMapper.toResponseList(cargoRepository.findByRegistradoEn(registradoEn));
    }

    public CargoResponse create(CargoRequest request) {
        Factura factura = facturaRepository.findByNumeroFactura(request.getNumeroFactura())
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con numero: " + request.getNumeroFactura()));

        Cargo cargo = cargoMapper.toEntity(request);
        cargo.setFactura(factura);
        cargo.setRegistradoEn(LocalDate.now());

        Cargo cargoGuardado = cargoRepository.save(cargo);

        return cargoMapper.toResponse(cargoGuardado);
    }

    public CargoResponse update(Long id, CargoRequest request) {
        Cargo cargo = getCargoById(id);

        Factura factura = facturaRepository.findByNumeroFactura(request.getNumeroFactura())
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con numero: " + request.getNumeroFactura()));

        cargoMapper.updateEntity(request, cargo);
        cargo.setFactura(factura);

        Cargo cargoActualizado = cargoRepository.save(cargo);

        return cargoMapper.toResponse(cargoActualizado);
    }

    public void deleteById(Long id) {
        Cargo cargo = getCargoById(id);
        cargoRepository.delete(cargo);
    }

    private Cargo getCargoById(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo no encontrado con id: " + id));
    }
}
