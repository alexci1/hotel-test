package cl.hilton.reportes.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiResponse {

    private Long id;
    private String codigoReporte;
    private String nombreReporte;
    private String nombre;
    private String descripcion;
    private Integer valorActual;
    private Integer valorObjetivo;
    private String unidad;
    private String periodo;
    private LocalDate actualizadoEn;
}