package entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private Integer idCliente;
    private String nombre;
    private String email;

}
