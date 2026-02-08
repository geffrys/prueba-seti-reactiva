package co.com.bancolombia.r2dbc.repository;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import co.com.bancolombia.r2dbc.entities.Franchise;

public interface FranchiseReactiveRepository extends ReactiveCrudRepository<Franchise, Long>, ReactiveQueryByExampleExecutor<Franchise> {
}