package co.com.bancolombia.jpa.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import co.com.bancolombia.jpa.entity.Product;


public interface ProductRepository extends CrudRepository<Product, Long>, QueryByExampleExecutor<Product> {
}