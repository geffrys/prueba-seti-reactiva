package co.com.bancolombia.usecase.producto;

import co.com.bancolombia.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DeleteProductoSucursalUseCase {
        private final ProductoRepository productoRepository;
    
        public Mono<Boolean> execute(Long sucursalId, Long productoId) {
            return productoRepository.findById(productoId)
                    .switchIfEmpty(Mono.error(new RuntimeException("Producto not found with id: " + productoId)))
                    .flatMap(producto -> {
                        if (!producto.getSucursalId().equals(sucursalId)) {
                            return Mono.error(new RuntimeException("Producto with id: " + productoId + " does not belong to sucursal with id: " + sucursalId));
                        }
                        return productoRepository.deleteById(productoId)
                                .thenReturn(true);
                    });
        }
}
