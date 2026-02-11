package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.r2dbc.entities.Franchise;
import co.com.bancolombia.r2dbc.repository.FranchiseReactiveRepository;
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

class FranchiseAdapterTest {

    private FranchiseReactiveRepository repository;
    private ObjectMapper mapper;
    private FranchiseAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(FranchiseReactiveRepository.class);
        mapper = Mockito.mock(ObjectMapper.class);
        adapter = new FranchiseAdapter(repository, mapper);
    }

    @Test
    void saveMapsDomainToData() {
        Franquicia domain = new Franquicia(1L, "Franchise A");
        Franchise data = new Franchise();
        data.setId(1L);
        data.setName("Franchise A");

        when(repository.save(any(Franchise.class))).thenReturn(Mono.just(data));

        StepVerifier.create(adapter.save(domain))
                .expectNextMatches(result -> result.getId().equals(1L)
                        && "Franchise A".equals(result.getNombre()))
                .verifyComplete();

        ArgumentCaptor<Franchise> captor = ArgumentCaptor.forClass(Franchise.class);
        verify(repository).save(captor.capture());
        assertEquals(1L, captor.getValue().getId());
        assertEquals("Franchise A", captor.getValue().getName());
    }

    @Test
    void findByIdMapsDataToDomain() {
        Franchise data = new Franchise();
        data.setId(2L);
        data.setName("Franchise B");

        when(repository.findById(2L)).thenReturn(Mono.just(data));

        StepVerifier.create(adapter.findById(2L))
                .expectNextMatches(result -> result.getId().equals(2L)
                        && "Franchise B".equals(result.getNombre()))
                .verifyComplete();
    }

    @Test
    void findAllMapsDataToDomain() {
        Franchise data1 = new Franchise();
        data1.setId(1L);
        data1.setName("F1");
        Franchise data2 = new Franchise();
        data2.setId(2L);
        data2.setName("F2");

        when(repository.findAll()).thenReturn(Flux.just(data1, data2));

        StepVerifier.create(adapter.findAll())
                .expectNextMatches(result -> result.getId().equals(1L)
                        && "F1".equals(result.getNombre()))
                .expectNextMatches(result -> result.getId().equals(2L)
                        && "F2".equals(result.getNombre()))
                .verifyComplete();
    }
}
