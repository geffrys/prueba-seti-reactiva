package co.com.bancolombia.jpa.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import co.com.bancolombia.jpa.entity.Branch;

public interface BranchRepository extends CrudRepository<Branch, Long>, QueryByExampleExecutor<Branch> {
}
