package co.com.bancolombia.usecase.producto;

import co.com.bancolombia.model.producto.gateways.ProductoRepository;
import co.com.bancolombia.model.sucursal.gateways.SucursalRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import co.com.bancolombia.model.sucursal.ProductMaxStockBySucursal;

@RequiredArgsConstructor
public class GetMaxStockProductsByFranquiciaUseCase {

    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;

    public Flux<ProductMaxStockBySucursal> execute(Long franquiciaId) {

        return sucursalRepository.findByFranquiciaId(franquiciaId)

                .flatMap(sucursal ->
                        productoRepository.findBySucursalId(sucursal.getId())

                                // sacar el de mayor stock
                                .sort((p1, p2) -> Integer.compare(
                                        p2.getStock(),
                                        p1.getStock()
                                ))
                                .next() // toma el primero del flux

                                .map(producto ->
                                        new ProductMaxStockBySucursal(
                                                sucursal.getId(),
                                                sucursal.getNombre(),
                                                producto
                                        )
                                )
                );
    }
}
