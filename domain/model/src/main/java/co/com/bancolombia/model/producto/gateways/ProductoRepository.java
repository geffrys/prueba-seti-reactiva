package co.com.bancolombia.model.producto.gateways;
import co.com.bancolombia.model.producto.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductoRepository {
    Flux<Producto> findAll();
    Mono<Producto> save(Producto producto);
    Mono<Producto> findById(Long id);  
    Flux<Producto> findBySucursalId(Long sucursalId);
}
