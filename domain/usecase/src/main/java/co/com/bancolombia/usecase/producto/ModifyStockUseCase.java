package co.com.bancolombia.usecase.producto;

import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ModifyStockUseCase {
    private final ProductoRepository productoRepository;

    public Mono<Producto> execute(Long productoId, int stock) {
        return productoRepository.findById(productoId)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto not found with id: " + productoId)))
                .flatMap(producto -> {
                    if (stock < 0) {
                        return Mono.error(new RuntimeException("Stock cannot be negative"));
                    }
                    producto.setStock(stock);
                    return productoRepository.save(producto);
                });
    }
    
}
