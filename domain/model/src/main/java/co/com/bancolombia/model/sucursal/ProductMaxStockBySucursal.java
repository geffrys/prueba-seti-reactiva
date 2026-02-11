package co.com.bancolombia.model.sucursal;

import co.com.bancolombia.model.producto.Producto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ProductMaxStockBySucursal  {
    private final Long sucursalId;
    private final String nombreSucursal;
    private final Producto producto;
}
