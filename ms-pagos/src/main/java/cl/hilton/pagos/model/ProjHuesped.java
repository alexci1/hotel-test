package cl.hilton.pagos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proj_huesped")
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

    @Builder.Default
    @OneToMany(mappedBy = "huesped", fetch = FetchType.LAZY)
    private List<Factura> facturas = new ArrayList<>();
}
