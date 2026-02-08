package co.com.bancolombia.r2dbc.repository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import co.com.bancolombia.r2dbc.entities.Branch;

public interface BranchReactiveRepository extends ReactiveCrudRepository<Branch, Long>, ReactiveQueryByExampleExecutor<Branch> {
}
