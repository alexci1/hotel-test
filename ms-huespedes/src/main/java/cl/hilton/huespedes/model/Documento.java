package cl.hilton.huespedes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "documentos",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_documentos", columnNames = {"tipo", "numero", "pais_emisor"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email_huesped", referencedColumnName = "email", nullable = false)
    private Huesped huesped;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "numero", nullable = false, length = 40)
    private String numero;

    @Column(name = "pais_emisor", nullable = false, length = 2)
    private String paisEmisor;

    @Column(name = "vencimiento")
    private LocalDate vencimiento;
}
