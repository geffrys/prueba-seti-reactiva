package co.com.bancolombia.usecase.producto;

import co.com.bancolombia.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DeleteProductoUseCase {
    private final ProductoRepository productoRepository;

    public Mono<Boolean> execute(Long productoId) {
        return productoRepository.findById(productoId)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto not found with id: " + productoId)))
                .flatMap(producto -> productoRepository.deleteById(productoId)
                        .thenReturn(true))
                .onErrorReturn(false);
    }
}
