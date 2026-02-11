package co.com.bancolombia.model.sucursal.gateways;
import co.com.bancolombia.model.sucursal.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalRepository {
    Mono<Sucursal> findById(Long id);
    Flux<Sucursal> findAll();
    Mono<Sucursal> save(Sucursal sucursal);
    Flux<Sucursal> findByFranquiciaId(Long franquiciaId);
}
