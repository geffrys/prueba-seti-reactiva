package co.com.bancolombia.usecase.producto;

import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetProductosSucursalUseCase {

    private final ProductoRepository repository;

    public Flux<Producto> execute(Long sucursalId) {
        return repository.findBySucursalId(sucursalId);
    }
    
}
