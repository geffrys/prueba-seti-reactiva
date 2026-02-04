package co.com.bancolombia.model.franquicia;
import lombok.Builder;

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
public class Franquicia {
    private String id;
    private String nombre;
    private List<Sucursal> sucursales;
}
