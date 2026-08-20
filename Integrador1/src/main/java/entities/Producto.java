package entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    private Integer idProducto;
    private String nombre;
    private float valor;
}
