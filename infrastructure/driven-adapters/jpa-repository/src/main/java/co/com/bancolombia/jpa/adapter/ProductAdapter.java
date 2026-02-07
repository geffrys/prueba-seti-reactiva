package co.com.bancolombia.jpa.adapter;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.jpa.repository.ProductRepository;
import co.com.bancolombia.jpa.entity.Product;
import co.com.bancolombia.model.producto.Producto;

@Repository
public class ProductAdapter extends AdapterOperations<Producto, Product, Long, ProductRepository> {
    public ProductAdapter(ProductRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Producto.class));
    }
}

