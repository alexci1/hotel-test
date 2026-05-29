package cl.hilton.huespedes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.huespedes.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByHuespedEmail(String emailHuesped);

    List<Documento> findByTipo(String tipo);

    List<Documento> findByPaisEmisor(String paisEmisor);

    List<Documento> findByVencimiento(LocalDate vencimiento);

    Optional<Documento> findByTipoAndNumeroAndPaisEmisor(String tipo, String numero, String paisEmisor);

    boolean existsByTipoAndNumeroAndPaisEmisor(String tipo, String numero, String paisEmisor);
}
