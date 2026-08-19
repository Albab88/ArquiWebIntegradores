package entities;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    private Integer idFactura;
    private Cliente cliente;
    private Map<Producto, Integer> productos;
}
