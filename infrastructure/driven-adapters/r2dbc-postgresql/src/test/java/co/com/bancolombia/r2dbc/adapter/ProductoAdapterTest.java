package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.r2dbc.entities.Product;
import co.com.bancolombia.r2dbc.repository.ProductReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductoAdapterTest {

    private ProductReactiveRepository repository;
    private ObjectMapper mapper;
    private ProductoAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ProductReactiveRepository.class);
        mapper = Mockito.mock(ObjectMapper.class);
        adapter = new ProductoAdapter(repository, mapper);
    }

    @Test
    void saveMapsDomainToData() {
        Producto domain = new Producto(100L, "Prod 1", 7, 3L);
        Product data = new Product();
        data.setId(100L);
        data.setName("Prod 1");
        data.setStock(7);
        data.setBranchId(3L);

        when(repository.save(any(Product.class))).thenReturn(Mono.just(data));

        StepVerifier.create(adapter.save(domain))
                .expectNextMatches(result -> result.getId().equals(100L)
                        && "Prod 1".equals(result.getNombre())
                        && result.getStock().equals(7)
                        && result.getSucursalId().equals(3L))
                .verifyComplete();

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).save(captor.capture());
        assertEquals(100L, captor.getValue().getId());
        assertEquals("Prod 1", captor.getValue().getName());
        assertEquals(7, captor.getValue().getStock());
        assertEquals(3L, captor.getValue().getBranchId());
    }

    @Test
    void findBySucursalIdMapsDataToDomain() {
        Product data1 = new Product();
        data1.setId(1L);
        data1.setName("P1");
        data1.setStock(2);
        data1.setBranchId(8L);
        Product data2 = new Product();
        data2.setId(2L);
        data2.setName("P2");
        data2.setStock(5);
        data2.setBranchId(8L);

        when(repository.findByBranchId(8L)).thenReturn(Flux.just(data1, data2));

        StepVerifier.create(adapter.findBySucursalId(8L))
                .expectNextMatches(result -> result.getId().equals(1L)
                        && "P1".equals(result.getNombre())
                        && result.getStock().equals(2)
                        && result.getSucursalId().equals(8L))
                .expectNextMatches(result -> result.getId().equals(2L)
                        && "P2".equals(result.getNombre())
                        && result.getStock().equals(5)
                        && result.getSucursalId().equals(8L))
                .verifyComplete();
    }

    @Test
    void deleteByIdDelegatesToRepository() {
        when(repository.deleteById(9L)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById(9L))
                .verifyComplete();

        verify(repository).deleteById(9L);
    }
}
