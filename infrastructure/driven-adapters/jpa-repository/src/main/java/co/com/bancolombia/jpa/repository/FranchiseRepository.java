package co.com.bancolombia.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import co.com.bancolombia.jpa.entity.Franchise;

public interface FranchiseRepository extends CrudRepository<Franchise, Long>, QueryByExampleExecutor<Franchise> {
}