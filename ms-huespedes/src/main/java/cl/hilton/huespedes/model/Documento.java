package cl.hilton.huespedes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
    name = "documento",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_doc",
            columnNames = {"tipo", "numero", "pais_emisor"}
        )
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
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "email_huesped",
        referencedColumnName = "email",
        nullable = false
    )
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