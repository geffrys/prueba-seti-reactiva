package co.com.bancolombia.jpa.adapter;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import co.com.bancolombia.jpa.entity.Franchise;
import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.jpa.repository.FranchiseRepository;
import co.com.bancolombia.model.franquicia.Franquicia;

@Repository
public class FranchiseAdapter extends AdapterOperations<Franquicia, Franchise, Long, FranchiseRepository> {
    public FranchiseAdapter(FranchiseRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franquicia.class));
    }
}

