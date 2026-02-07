package co.com.bancolombia.jpa.adapter;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.jpa.repository.BranchRepository;
import co.com.bancolombia.jpa.entity.Branch;
import co.com.bancolombia.model.sucursal.Sucursal;
@Repository
public class BranchAdapter extends AdapterOperations<Sucursal, Branch, Long, BranchRepository> {
    public BranchAdapter(BranchRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Sucursal.class));
    }
}

