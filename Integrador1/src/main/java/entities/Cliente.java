package entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    private Integer idCliente;
    private String nombre;
    private String email;
}
