package co.com.bancolombia.usecase.franquicia;


import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.model.franquicia.gateways.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;


@RequiredArgsConstructor
public class GetFranquiciasUseCase {
    private final FranquiciaRepository repository;

    public Flux<Franquicia> execute() {
        return repository.findAll();
    }
}
