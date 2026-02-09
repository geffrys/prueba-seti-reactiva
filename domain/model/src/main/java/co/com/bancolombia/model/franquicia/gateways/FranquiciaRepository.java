package co.com.bancolombia.model.franquicia.gateways;
import co.com.bancolombia.model.franquicia.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface FranquiciaRepository {
    Mono<Franquicia> findById(Long id);
    Flux<Franquicia> findAll();
    Mono<Franquicia> save(Franquicia franquicia);
}
