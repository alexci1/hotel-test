package cl.hilton.checkin.repository;

import cl.hilton.checkin.model.ProjHuesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjHuespedRepository extends JpaRepository<ProjHuesped, String> {

    List<ProjHuesped> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}