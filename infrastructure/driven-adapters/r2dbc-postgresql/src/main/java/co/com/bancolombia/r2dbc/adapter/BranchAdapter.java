package co.com.bancolombia.r2dbc.adapter;


import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.model.sucursal.gateways.SucursalRepository;
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
> implements SucursalRepository {
    public BranchAdapter(BranchReactiveRepository repository, ObjectMapper mapper){
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, BranchAdapter::toDomain);
    }

    
    private static Sucursal toDomain(Branch data) {
        return new Sucursal(
            data.getId(),
            data.getName(),
            data.getFranchiseId()
        );
    }

    @Override
    protected Branch toData(Sucursal domain) {
        Branch data = new Branch();
        data.setId(domain.getId());
        data.setName(domain.getNombre());
        return data;
    }

    
}