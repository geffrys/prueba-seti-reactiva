package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.r2dbc.entities.Branch;
import co.com.bancolombia.r2dbc.repository.BranchReactiveRepository;
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

class BranchAdapterTest {

    private BranchReactiveRepository repository;
    private ObjectMapper mapper;
    private BranchAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(BranchReactiveRepository.class);
        mapper = Mockito.mock(ObjectMapper.class);
        adapter = new BranchAdapter(repository, mapper);
    }

    @Test
    void saveMapsDomainToData() {
        Sucursal domain = new Sucursal(10L, "Sucursal 1", 5L);
        Branch data = new Branch();
        data.setId(10L);
        data.setName("Sucursal 1");
        data.setFranchiseId(5L);

        when(repository.save(any(Branch.class))).thenReturn(Mono.just(data));

        StepVerifier.create(adapter.save(domain))
                .expectNextMatches(result -> result.getId().equals(10L)
                        && "Sucursal 1".equals(result.getNombre())
                        && result.getFranquiciaId().equals(5L))
                .verifyComplete();

        ArgumentCaptor<Branch> captor = ArgumentCaptor.forClass(Branch.class);
        verify(repository).save(captor.capture());
        assertEquals(10L, captor.getValue().getId());
        assertEquals("Sucursal 1", captor.getValue().getName());
        assertEquals(5L, captor.getValue().getFranchiseId());
    }

    @Test
    void findByFranquiciaIdMapsDataToDomain() {
        Branch data1 = new Branch();
        data1.setId(1L);
        data1.setName("S1");
        data1.setFranchiseId(9L);
        Branch data2 = new Branch();
        data2.setId(2L);
        data2.setName("S2");
        data2.setFranchiseId(9L);

        when(repository.findByFranchiseId(9L)).thenReturn(Flux.just(data1, data2));

        StepVerifier.create(adapter.findByFranquiciaId(9L))
                .expectNextMatches(result -> result.getId().equals(1L)
                        && "S1".equals(result.getNombre())
                        && result.getFranquiciaId().equals(9L))
                .expectNextMatches(result -> result.getId().equals(2L)
                        && "S2".equals(result.getNombre())
                        && result.getFranquiciaId().equals(9L))
                .verifyComplete();
    }
}
