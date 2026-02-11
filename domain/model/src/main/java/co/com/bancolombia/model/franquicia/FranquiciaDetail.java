package co.com.bancolombia.model.franquicia;

import java.util.List;

import co.com.bancolombia.model.sucursal.Sucursal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class FranquiciaDetail {
    private Franquicia franquicia;
    private List<Sucursal> sucursales;
}
