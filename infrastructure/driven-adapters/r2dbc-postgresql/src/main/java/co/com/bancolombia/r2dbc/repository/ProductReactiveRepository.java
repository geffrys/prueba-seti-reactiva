package co.com.bancolombia.r2dbc.repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

import co.com.bancolombia.r2dbc.entities.Product;


public interface ProductReactiveRepository extends ReactiveCrudRepository<Product, Long>, ReactiveQueryByExampleExecutor<Product> {
}