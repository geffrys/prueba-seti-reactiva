package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.r2dbc.entities.Branch;
import co.com.bancolombia.r2dbc.repository.BranchReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;

@Repository
public class BranchAdapter extends ReactiveAdapterOperations<
    Sucursal/* change for domain model */,
    Branch /* change for adapter model */,
    Long,
    BranchReactiveRepository
> {
    public BranchAdapter(BranchReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Sucursal.class/* change for domain model */));
    }

}