package cl.hilton.pagos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proj_huespedes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHuesped {

    @Id
    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDate actualizadoEn;

    @OneToMany(mappedBy = "huesped", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Factura> facturas = new ArrayList<>();
}
