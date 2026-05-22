package cl.hilton.reservas.repository;

import cl.hilton.reservas.model.ProjHuesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjHuespedRepository extends JpaRepository<ProjHuesped, String> {

}