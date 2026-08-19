package entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Integer idProducto;
    private String nombre;
    private float valor;
}
