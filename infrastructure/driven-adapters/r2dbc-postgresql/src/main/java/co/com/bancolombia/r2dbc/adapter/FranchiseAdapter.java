package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.r2dbc.repository.FranchiseReactiveRepository;
import org.springframework.stereotype.Repository;
import co.com.bancolombia.r2dbc.entities.Franchise;
import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;

@Repository
public class FranchiseAdapter extends ReactiveAdapterOperations<
    Franquicia/* change for domain model */,
    Franchise/* change for adapter model */,
    Long,
    FranchiseReactiveRepository
> {
    public FranchiseAdapter(FranchiseReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Franquicia.class/* change for domain model */));
    }

}