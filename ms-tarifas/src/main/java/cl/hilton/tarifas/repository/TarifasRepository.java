package cl.hilton.tarifas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.tarifas.model.Tarifa;

@Repository
public interface TarifasRepository extends JpaRepository<Tarifa, Long> {

    Optional<Tarifa> findByTemporadaCodigoAndTipoHabitacionCodigo(String codigoTemporada, String tipoHabitacion);

    boolean existsByTemporadaCodigoAndTipoHabitacionCodigo(String codigoTemporada, String tipoHabitacion);

    List<Tarifa> findByTemporadaCodigo(String codigoTemporada);

    List<Tarifa> findByTipoHabitacionCodigo(String tipoHabitacion);

    List<Tarifa> findByActiva(Boolean activa);

    List<Tarifa> findByIncluyeDesayuno(Boolean incluyeDesayuno);

    List<Tarifa> findByTipoHabitacionCodigoAndActiva(String tipoHabitacion, Boolean activa);
}
