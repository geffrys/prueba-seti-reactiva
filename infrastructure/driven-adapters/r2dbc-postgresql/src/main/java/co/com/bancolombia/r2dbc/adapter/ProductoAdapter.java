package co.com.bancolombia.r2dbc.adapter;
import org.springframework.stereotype.Repository;
import org.reactivecommons.utils.ObjectMapper;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.entities.Product;
import co.com.bancolombia.r2dbc.repository.ProductReactiveRepository;
import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.model.producto.gateways.ProductoRepository;


@Repository
public class ProductoAdapter extends ReactiveAdapterOperations<
    Producto/* change for domain model */,
    Product/* change for adapter model */,
    Long,
    ProductReactiveRepository
> implements ProductoRepository{
    public ProductoAdapter(ProductReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, ProductoAdapter::toDomain);
    }

    private static Producto toDomain(Product data) {
        return new Producto(
            data.getId(),
            data.getName(),
            data.getStock(),
            data.getBranchId()
        );
    }

    @Override
    protected Product toData(Producto domain) {
        Product data = new Product();
        data.setId(domain.getId());
        data.setName(domain.getNombre());
        data.setStock(domain.getStock());
        data.setBranchId(domain.getSucursalId());
        return data;
    }

}
