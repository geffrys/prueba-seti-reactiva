package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.r2dbc.repository.FranchiseReactiveRepository;

import org.springframework.stereotype.Repository;
import co.com.bancolombia.r2dbc.entities.Franchise;
import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.model.franquicia.gateways.FranquiciaRepository;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;

@Repository
public class FranchiseAdapter extends ReactiveAdapterOperations<
    Franquicia/* change for domain model */,
    Franchise/* change for adapter model */,
    Long,
    FranchiseReactiveRepository
> implements FranquiciaRepository{
    public FranchiseAdapter(FranchiseReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, FranchiseAdapter::toDomain);
    }


    private static Franquicia toDomain(Franchise data) {
        return new Franquicia(
            data.getId(),
            data.getName()
        );
    }

    @Override
    protected Franchise toData(Franquicia domain) {
        Franchise data = new Franchise();
        data.setId(domain.getId());
        data.setName(domain.getNombre());
        return data;
    }
}