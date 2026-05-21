package cl.hilton.tarifas.repository;

import cl.hilton.tarifas.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifasRepository extends JpaRepository<Tarifa, Long> {

    List<Tarifa> findByActiva(Boolean activa);

    List<Tarifa> findByIncluyeDesayuno(Boolean incluyeDesayuno);

}
