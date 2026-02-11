package co.com.bancolombia.r2dbc.repository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import co.com.bancolombia.r2dbc.entities.Branch;
import reactor.core.publisher.Flux;

@Repository
public interface BranchReactiveRepository extends ReactiveCrudRepository<Branch, Long>, ReactiveQueryByExampleExecutor<Branch> {
    Flux<Branch> findByFranchiseId(Long franchiseId);
}
