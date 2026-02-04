package co.com.bancolombia.model.sucursal;
import lombok.Builder;
import co.com.bancolombia.model.producto.Producto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Sucursal {
    private Long id;
    private String nombre;
    private List<Producto> productos;
}
