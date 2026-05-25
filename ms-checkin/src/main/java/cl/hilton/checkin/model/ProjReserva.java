package cl.hilton.checkin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proj_reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjReserva {

    @Id
    @Column(name = "codigo_reserva", nullable = false, length = 20)
    private String codigoReserva;

    @Column(name = "email_huesped", nullable = false, length = 120)
    private String emailHuesped;

    @Column(name = "numero_habitacion", nullable = false, length = 10)
    private String numeroHabitacion;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDate actualizadoEn;

    @OneToOne(mappedBy = "reserva")
    private Checkin checkin;

    @OneToOne(mappedBy = "reserva")
    private Checkout checkout;

    @OneToMany(mappedBy = "reserva")
    @Builder.Default
    private List<Llave> llaves = new ArrayList<>();
}