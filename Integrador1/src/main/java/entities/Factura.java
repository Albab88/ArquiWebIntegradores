package entities;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {
    private Integer idFactura;
    private Cliente cliente;
    @Builder.Default
    private Map<Producto, Integer> productos = new HashMap<>();
}
