package cl.hilton.reportes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String frecuencia;
    private Boolean activo;
}