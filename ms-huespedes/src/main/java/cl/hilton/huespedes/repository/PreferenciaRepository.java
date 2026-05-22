package cl.hilton.huespedes.repository;

import cl.hilton.huespedes.model.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Long> {

    Optional<Preferencia> findByHuespedEmail(String emailHuesped);

    boolean existsByHuespedEmail(String emailHuesped);

    List<Preferencia> findByTipoCama(String tipoCama);
}