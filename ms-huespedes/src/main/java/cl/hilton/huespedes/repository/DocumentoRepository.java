package cl.hilton.huespedes.repository;

import cl.hilton.huespedes.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByHuespedEmail(String emailHuesped);

    List<Documento> findByTipo(String tipo);

    List<Documento> findByPaisEmisor(String paisEmisor);

    boolean existsByTipoAndNumeroAndPaisEmisor(String tipo, String numero, String paisEmisor);
}