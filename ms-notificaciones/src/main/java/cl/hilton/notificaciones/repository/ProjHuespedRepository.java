package cl.hilton.notificaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.notificaciones.model.ProjHuesped;

@Repository
public interface ProjHuespedRepository extends JpaRepository<ProjHuesped, String> {

    Optional<ProjHuesped> findByEmail(String email);
}