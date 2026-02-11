package co.com.bancolombia.r2dbc.repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

import co.com.bancolombia.r2dbc.entities.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductReactiveRepository extends ReactiveCrudRepository<Product, Long>, ReactiveQueryByExampleExecutor<Product> {
    Flux<Product> findByBranchId(Long branchId);
    Mono<Void> deleteById(Long id);
}